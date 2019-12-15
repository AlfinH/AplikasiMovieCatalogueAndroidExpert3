package com.alfin.aplikasimoviecatalogueandroidexpert3.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.activity.DetailMovieTvShowActivity;
import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.adapter.MovieTvShowAdapter;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<MovieTvShow> list = new ArrayList<>();

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

        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);

//        list.addAll(getListMovies());
        showRecyclerView();
    }

    private void showRecyclerView(){
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieTvShowAdapter movieTvShowAdapter = new MovieTvShowAdapter(list);
        rvMovies.setAdapter(movieTvShowAdapter);

        movieTvShowAdapter.setOnItemClickCallback(new MovieTvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieTvShow data) {
                showSelectedMovieTvShow(data);
            }
        });
    }

    private void showSelectedMovieTvShow(MovieTvShow data) {
        Intent intent = new Intent(getContext(), DetailMovieTvShowActivity.class);
        intent.putExtra(DetailMovieTvShowActivity.EXTRA_MOVIE_TVSHOW,data);
        startActivity(intent);
    }

//    private ArrayList<MovieTvShow> getListMovies() {
//        TypedArray dataGambar = getResources().obtainTypedArray(R.array.gambar_movie);
//        String[] dataName = getResources().getStringArray(R.array.judul_movie);
//        String[] dataDate = getResources().getStringArray(R.array.tgl_release_movie);
//        String[] dataGenre = getResources().getStringArray(R.array.genre_movie);
//        String[] dataDescription = getResources().getStringArray(R.array.deskripsi_movie);
//        ArrayList<MovieTvShow> listMovieTvShow = new ArrayList<>();
//        for (int i = 0; i < dataName.length; i++) {
//            MovieTvShow movieTvShow = new MovieTvShow();
//            movieTvShow.setGambar(dataGambar.getResourceId(i,0));
//            movieTvShow.setJudul(dataName[i]);
//            movieTvShow.setTanggal_rilis(dataDate[i]);
//            movieTvShow.setGenre(dataGenre[i]);
//            movieTvShow.setDeskripsi(dataDescription[i]);
//            listMovieTvShow.add(movieTvShow);
//        }
//        return listMovieTvShow;
//    }
}
