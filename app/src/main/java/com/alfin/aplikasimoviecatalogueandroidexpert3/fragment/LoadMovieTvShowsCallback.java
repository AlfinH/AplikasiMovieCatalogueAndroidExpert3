package com.alfin.aplikasimoviecatalogueandroidexpert3.fragment;

import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;

import java.util.ArrayList;

public interface LoadMovieTvShowsCallback {
    void preExecute();
    void postExecute(ArrayList<MovieTvShow> movieTvShows);
}
