package com.alfin.aplikasimoviecatalogueandroidexpert3.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alfin.aplikasimoviecatalogueandroidexpert3.BuildConfig;
import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.helper.MappingHelper;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.alfin.aplikasimoviecatalogueandroidexpert3.widget.FavoritesBannerWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.CONTENT_URI;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.DESCRIPTION;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.GAMBAR;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.GENRE;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.ID;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.RELEASE_DATE;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract.MovieTvShowColumns.TITLE;

public class DetailMovieTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_TVSHOW = "movie_tvshow";

    private TextView txtJudul, txtGenre, txtTanggal, txtDeskripsi;
    private ImageView imgGambar;

    private Menu menu;
    private boolean isFavorite = false;

    private Uri uriWithId;
    private MovieTvShow movieTvShow;

    public static final int REQUEST_ADD = 100;
    public static final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_tv_show);

        movieTvShow = getIntent().getParcelableExtra(EXTRA_MOVIE_TVSHOW);
        if (movieTvShow == null) {
            movieTvShow = new MovieTvShow();
        } else {
            uriWithId = Uri.parse(CONTENT_URI + "/" + movieTvShow.getId());
            if (uriWithId != null) {
                Cursor cursor = getContentResolver().query(uriWithId, null, null, null, null);
                if (cursor != null) {
                    MovieTvShow temp = MappingHelper.mapCursorToObject(cursor);
                    if (temp != null) {
                        isFavorite = true;
                    }
                    cursor.close();
                }
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtJudul = findViewById(R.id.tv_judul_detail);
        txtGenre = findViewById(R.id.tv_genre_detail);
        txtTanggal = findViewById(R.id.tv_tanggal_detail);
        txtDeskripsi = findViewById(R.id.tv_deskripsi_detail);
        imgGambar = findViewById(R.id.iv_gambar_detail);

        if (movieTvShow != null) {
            txtJudul.setText(movieTvShow.getJudul());
            txtGenre.setText(movieTvShow.getGenre());
            txtTanggal.setText(movieTvShow.getTanggal_rilis());
            txtDeskripsi.setText(movieTvShow.getDeskripsi());
            Glide.with(getApplicationContext())
                    .load(BuildConfig.BASE_URL_ORI + movieTvShow.getGambar())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_error).error(R.drawable.ic_error).override(175, 250))
                    .into(imgGambar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        changeMenuIcon();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fav:
                isFavorite = false;
                getContentResolver().delete(uriWithId, null, null);
                Toast.makeText(DetailMovieTvShowActivity.this, getResources().getString(R.string.teks_favorite_hapus), Toast.LENGTH_SHORT).show();
                changeMenuIcon();
                break;
            case R.id.action_fav_border:
                isFavorite = true;
                // Gunakan content uri untuk insert
                // content://com.alfin.mynotesapp/note/
                ContentValues values = new ContentValues();
                values.put(ID, movieTvShow.getId());
                values.put(TITLE, movieTvShow.getJudul());
                values.put(DESCRIPTION, movieTvShow.getDeskripsi());
                values.put(RELEASE_DATE, movieTvShow.getTanggal_rilis());
                values.put(GENRE, movieTvShow.getGenre());
                values.put(GAMBAR, movieTvShow.getGambar());
                getContentResolver().insert(CONTENT_URI, values);
                Toast.makeText(DetailMovieTvShowActivity.this, getResources().getString(R.string.teks_favorite_tambah), Toast.LENGTH_SHORT).show();
                changeMenuIcon();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeMenuIcon() {
        menu.findItem(R.id.action_fav).setVisible(isFavorite);
        menu.findItem(R.id.action_fav_border).setVisible(!isFavorite);
        updateAllWidgets();
    }

    private void updateAllWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoritesBannerWidget.class));
        if (appWidgetIds.length > 0) {
            new FavoritesBannerWidget().onUpdate(this, appWidgetManager, appWidgetIds);
        }
    }
}
