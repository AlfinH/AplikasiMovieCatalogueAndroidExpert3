package com.alfin.aplikasimoviecatalogueandroidexpert3;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private String KEY_TODAY = "today";
    private String KEY_DAILY = "daily";
    private String PREF_NAME = "aplikasimoviecatalogueandroidexpert3";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setToday(boolean status){
        editor.putBoolean(KEY_TODAY, status);
        editor.apply();
    }

    public void setDaily(boolean status){
        editor.putBoolean(KEY_DAILY, status);
        editor.apply();
    }

    public boolean isToday(){
        return preferences.getBoolean(KEY_TODAY,false);
    }

    public boolean isDaily(){
        return preferences.getBoolean(KEY_DAILY, false);
    }
}
