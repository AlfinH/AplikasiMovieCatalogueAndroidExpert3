package com.alfin.moshofavoriteapp.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alfin.moshofavoriteapp.BuildConfig;
import com.alfin.moshofavoriteapp.R;
import com.alfin.moshofavoriteapp.helper.MappingHelper;
import com.alfin.moshofavoriteapp.model.MovieTvShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.alfin.moshofavoriteapp.db.DatabaseContract.MovieTvShowColumns.CONTENT_URI;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
