package com.alfin.aplikasimoviecatalogueandroidexpert3.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailMovieTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_TVSHOW = "movie_tvshow";

    private TextView txtJudul,txtGenre,txtTanggal,txtDeskripsi;
    private ImageView imgGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_tv_show);

        MovieTvShow movieTvShow = getIntent().getParcelableExtra(EXTRA_MOVIE_TVSHOW);

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
}
