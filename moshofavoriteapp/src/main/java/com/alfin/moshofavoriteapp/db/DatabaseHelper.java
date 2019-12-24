package com.alfin.moshofavoriteapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmtsapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.MovieTvShowColumns.TABLE_NAME,
            DatabaseContract.MovieTvShowColumns.ID,
            DatabaseContract.MovieTvShowColumns.TITLE,
            DatabaseContract.MovieTvShowColumns.DESCRIPTION,
            DatabaseContract.MovieTvShowColumns.RELEASE_DATE,
            DatabaseContract.MovieTvShowColumns.GENRE,
            DatabaseContract.MovieTvShowColumns.GAMBAR
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MovieTvShowColumns.TABLE_NAME);
        onCreate(db);
    }
}
