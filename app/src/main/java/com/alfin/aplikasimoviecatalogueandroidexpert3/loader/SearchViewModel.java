package com.alfin.aplikasimoviecatalogueandroidexpert3.loader;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alfin.aplikasimoviecatalogueandroidexpert3.BuildConfig;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<MovieTvShow>> listMovieTvShows = new MutableLiveData<>();

    public void setSearchResults(final String language,final String input_string) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieTvShow> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/search/multi?api_key=" + API_KEY + "&language=" + language + "&query=" + input_string;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movieTvShow = list.getJSONObject(i);
                        MovieTvShow movieTvShowItems = new MovieTvShow(movieTvShow);

                        listItems.add(movieTvShowItems);
                    }
                    listMovieTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieTvShow>> getMovieTvShows() {
        return listMovieTvShows;
    }
}
