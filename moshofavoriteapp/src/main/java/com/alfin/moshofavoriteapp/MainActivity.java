package com.alfin.moshofavoriteapp;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.moshofavoriteapp.adapter.MovieTvShowAdapter;
import com.alfin.moshofavoriteapp.db.DatabaseContract;
import com.alfin.moshofavoriteapp.helper.MappingHelper;
import com.alfin.moshofavoriteapp.model.MovieTvShow;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieTvShowsCallback {

    public static ProgressBar progressBarFav;
    public static RecyclerView rvMovieTvShow;
    public static MovieTvShowAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarFav = findViewById(R.id.progressBar);
        rvMovieTvShow = findViewById(R.id.rv_favorites);
        rvMovieTvShow.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieTvShowAdapter(this);
        rvMovieTvShow.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.MovieTvShowColumns.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadMovieTvShowAsync(this, this).execute();
        } else {
            ArrayList<MovieTvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovieTvShows(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovieTvShows());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBarFav.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieTvShow> movieTvShows) {
        progressBarFav.setVisibility(View.INVISIBLE);
        if (movieTvShows.size() > 0) {
            adapter.setListMovieTvShows(movieTvShows);
        } else {
            adapter.setListMovieTvShows(new ArrayList<MovieTvShow>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadMovieTvShowAsync extends AsyncTask<Void, Void, ArrayList<MovieTvShow>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieTvShowsCallback> weakCallback;

        private LoadMovieTvShowAsync(Context context, LoadMovieTvShowsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieTvShow> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.MovieTvShowColumns.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<MovieTvShow> movieTvShows) {
            super.onPostExecute(movieTvShows);
            weakCallback.get().postExecute(movieTvShows);
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovieTvShow, message, Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieTvShowAsync(context, (LoadMovieTvShowsCallback) context).execute();
        }
    }
}

interface LoadMovieTvShowsCallback {
    void preExecute();

    void postExecute(ArrayList<MovieTvShow> movieTvShows);
}
