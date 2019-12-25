package com.alfin.aplikasimoviecatalogueandroidexpert3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        llLanguage = findViewById(R.id.language_setting);

        llLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upcoming_notif:

                break;
            case R.id.daily_notif:

                break;
            case R.id.language_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }
}
