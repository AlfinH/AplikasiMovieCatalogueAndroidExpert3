package com.alfin.aplikasimoviecatalogueandroidexpert3.helper;

import android.database.Cursor;

import com.alfin.aplikasimoviecatalogueandroidexpert3.db.DatabaseContract;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<MovieTvShow> mapCursorToArrayList(Cursor movieTvShowsCursor) {
        ArrayList<MovieTvShow> movieTvShowsList = new ArrayList<>();

        while (movieTvShowsCursor.moveToNext()) {
            int id = movieTvShowsCursor.getInt(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.ID));
            String title = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.TITLE));
            String description = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.DESCRIPTION));
            String release_date = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.RELEASE_DATE));
            String genre = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.GENRE));
            String gambar = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.GAMBAR));
            movieTvShowsList.add(new MovieTvShow(id, gambar, title, release_date, genre, description));
        }

        return movieTvShowsList;
    }

    public static MovieTvShow mapCursorToObject(Cursor movieTvShowsCursor) {
        movieTvShowsCursor.moveToFirst();
        if(movieTvShowsCursor.getCount() > 0){
            int id = movieTvShowsCursor.getInt(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.ID));
            String title = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.TITLE));
            String description = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.DESCRIPTION));
            String release_date = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.RELEASE_DATE));
            String genre = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.GENRE));
            String gambar = movieTvShowsCursor.getString(movieTvShowsCursor.getColumnIndexOrThrow(DatabaseContract.MovieTvShowColumns.GAMBAR));
            return new MovieTvShow(id, gambar, title, release_date, genre, description);
        }
        return null;
    }
}
