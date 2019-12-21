package com.alfin.aplikasimoviecatalogueandroidexpert3.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailMovieTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_TVSHOW = "movie_tvshow";

    private TextView txtJudul,txtGenre,txtTanggal,txtDeskripsi;
    private ImageView imgGambar;

    private Menu menu;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_tv_show);

        MovieTvShow movieTvShow = getIntent().getParcelableExtra(EXTRA_MOVIE_TVSHOW);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtJudul = findViewById(R.id.tv_judul_detail);
        txtGenre = findViewById(R.id.tv_genre_detail);
        txtTanggal = findViewById(R.id.tv_tanggal_detail);
        txtDeskripsi = findViewById(R.id.tv_deskripsi_detail);
        imgGambar = findViewById(R.id.iv_gambar_detail);

        if(movieTvShow != null){
            txtJudul.setText(movieTvShow.getJudul());
            txtGenre.setText(movieTvShow.getGenre());
            txtTanggal.setText(movieTvShow.getTanggal_rilis());
            txtDeskripsi.setText(movieTvShow.getDeskripsi());
            Glide.with(getApplicationContext())
                    .load(movieTvShow.getGambar())
                    .apply(new RequestOptions().override(175, 250))
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
                changeMenuIcon();
                break;
            case R.id.action_fav_border:
                isFavorite=true;
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
    }

}
