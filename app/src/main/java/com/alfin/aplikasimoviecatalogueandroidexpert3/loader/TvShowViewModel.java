package com.alfin.aplikasimoviecatalogueandroidexpert3.loader;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alfin.aplikasimoviecatalogueandroidexpert3.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    private static final String API_KEY = "edd435e43acb8e79c6086d1bf4b5394d";
    private MutableLiveData<ArrayList<TvShow>> listTvShows = new MutableLiveData<>();

    public void setTvShows(final String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/tv/top_rated?api_key=" + API_KEY + "&language="+language+"&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShow tvShowItems = new TvShow(tvShow);

//                        tvShowItems.setId(tvShow.getInt("id"));
//                        tvShowItems.setGambar("https://image.tmdb.org/t/p/w185" + tvShow.getString("poster_path"));
//                        tvShowItems.setJudul(tvShow.getString("title"));
//                        tvShowItems.setTanggal_rilis(tvShow.getString("release_date"));
//                        tvShowItems.setDeskripsi(tvShow.getString("overview"));

                        listItems.add(tvShowItems);
                    }
                    listTvShows.postValue(listItems);
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
    public LiveData<ArrayList<TvShow>> getTvShows() {
        return listTvShows;
    }
}
