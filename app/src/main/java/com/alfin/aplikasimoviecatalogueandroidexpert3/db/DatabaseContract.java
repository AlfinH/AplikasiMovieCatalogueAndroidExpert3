package com.alfin.aplikasimoviecatalogueandroidexpert3.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.alfin.aplikasimoviecatalogueandroidexpert3";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class MovieTvShowColumns implements BaseColumns {
        public static final String TABLE_NAME = "mts";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String RELEASE_DATE = "date";
        public static final String GENRE = "genre";
        public static final String GAMBAR = "gambar";

        // untuk membuat URI content://com.alfin.aplikasimoviecatalogueandroidexpert3/mts
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
