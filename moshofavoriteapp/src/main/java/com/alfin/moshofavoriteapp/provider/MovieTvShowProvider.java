package com.alfin.moshofavoriteapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.alfin.moshofavoriteapp.db.MovieTvShowHelper;

import static com.alfin.moshofavoriteapp.db.DatabaseContract.AUTHORITY;
import static com.alfin.moshofavoriteapp.db.DatabaseContract.MovieTvShowColumns.CONTENT_URI;
import static com.alfin.moshofavoriteapp.db.DatabaseContract.MovieTvShowColumns.TABLE_NAME;

public class MovieTvShowProvider extends ContentProvider {

    /*
    Integer digunakan sebagai identifier antara select all sama select by id
     */
    private static final int MOVIETVSHOW = 1;
    private static final int MOVIETVSHOW_ID = 2;
    private MovieTvShowHelper movieTvShowHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
    misal
    uri com.dicoding.picodiploma.mynotesapp dicocokan dengan integer 1
    uri com.dicoding.picodiploma.mynotesapp/# dicocokan dengan integer 2
     */
    static {

        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIETVSHOW);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                MOVIETVSHOW_ID);
    }

    @Override
    public boolean onCreate() {
        movieTvShowHelper = MovieTvShowHelper.getInstance(getContext());
        movieTvShowHelper.open();
        return true;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIETVSHOW:
                cursor = movieTvShowHelper.queryAll();
                break;
            case MOVIETVSHOW_ID:
                cursor = movieTvShowHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIETVSHOW:
                added = movieTvShowHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIETVSHOW_ID:
                updated = movieTvShowHelper.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIETVSHOW_ID:
                deleted = movieTvShowHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }
}
