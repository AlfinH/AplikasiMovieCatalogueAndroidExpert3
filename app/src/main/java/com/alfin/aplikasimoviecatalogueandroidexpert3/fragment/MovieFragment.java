package com.alfin.aplikasimoviecatalogueandroidexpert3.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.activity.DetailMovieTvShowActivity;
import com.alfin.aplikasimoviecatalogueandroidexpert3.adapter.MovieAdapter;
import com.alfin.aplikasimoviecatalogueandroidexpert3.loader.MovieViewModel;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.Movie;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment{

    private MovieAdapter adapter;

    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                showSelectedMovie(data);
            }
        });

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);

        movieViewModel.setMovies(getResources().getString(R.string.language));
        showLoading(true);

        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movieItems) {
                if (movieItems != null) {
                    adapter.setData(movieItems);
                    showLoading(false);
                }
            }
        });
    }

    private void showSelectedMovie(Movie data) {
        Intent detailMovie = new Intent(getContext(), DetailMovieTvShowActivity.class);

        MovieTvShow movieTvShow = new MovieTvShow();
        movieTvShow.setGambar("https://image.tmdb.org/t/p/original" + data.getGambar());
        movieTvShow.setJudul(data.getJudul());
        movieTvShow.setTanggal_rilis(data.getJudul());
        movieTvShow.setGenre(data.getGenre());
        movieTvShow.setDeskripsi(data.getDeskripsi());

        detailMovie.putExtra(DetailMovieTvShowActivity.EXTRA_MOVIE_TVSHOW, movieTvShow);
        startActivity(detailMovie);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
