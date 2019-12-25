package com.alfin.aplikasimoviecatalogueandroidexpert3;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alfin.aplikasimoviecatalogueandroidexpert3.activity.SettingActivity;
import com.alfin.aplikasimoviecatalogueandroidexpert3.adapter.SectionsPagerAdapter;
import com.alfin.aplikasimoviecatalogueandroidexpert3.fragment.LoadMovieTvShowsCallback;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static com.alfin.aplikasimoviecatalogueandroidexpert3.fragment.FavoriteFragment.adapter;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.fragment.FavoriteFragment.progressBarFav;
import static com.alfin.aplikasimoviecatalogueandroidexpert3.fragment.FavoriteFragment.rvMovieTvShow;

public class MainActivity extends AppCompatActivity implements LoadMovieTvShowsCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovieTvShow, message, Snackbar.LENGTH_SHORT).show();
    }
}
