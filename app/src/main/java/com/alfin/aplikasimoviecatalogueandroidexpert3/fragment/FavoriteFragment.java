package com.alfin.aplikasimoviecatalogueandroidexpert3.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.adapter.MovieTvShowAdapter;
import com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract;
import com.alfin.aplikasimoviecatalogueandroidexpert3.helper.MappingHelper;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoadMovieTvShowsCallback {

    public static ProgressBar progressBarFav;
    public static RecyclerView rvMovieTvShow;
    public static MovieTvShowAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBarFav = view.findViewById(R.id.progressBar);
        rvMovieTvShow = view.findViewById(R.id.rv_favorites);
        rvMovieTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieTvShowAdapter(getActivity());
        rvMovieTvShow.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());
        getActivity().getContentResolver().registerContentObserver(DatabaseContract.MovieTvShowColumns.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadMovieTvShowAsync(getContext(), this).execute();
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
        getActivity().runOnUiThread(new Runnable() {
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



