package com.alfin.aplikasimoviecatalogueandroidexpert3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.alfin.aplikasimoviecatalogueandroidexpert3.AppPreference;
import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.reminder.AlarmReceiver;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llLanguage;
    private Switch dailySwitch,todaySwitch;
    private boolean isToday, isDaily;
    private AppPreference appPreference;

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        llLanguage = findViewById(R.id.language_setting);
        llLanguage.setOnClickListener(this);

        dailySwitch = findViewById(R.id.switch_daily);
        dailySwitch.setOnClickListener(this);

        todaySwitch = findViewById(R.id.switch_today);
        todaySwitch.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();
        appPreference = new AppPreference(this);

        setEnableDisableNotif();
    }

    private void setEnableDisableNotif(){
        if (appPreference.isDaily()){
            dailySwitch.setChecked(true);
        }else {
            dailySwitch.setChecked(false);
        }

        if (appPreference.isToday()){
            todaySwitch.setChecked(true);
        }else {
            todaySwitch.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_daily:
                isDaily = dailySwitch.isChecked();
                if(isDaily){
                    dailySwitch.setChecked(true);
                    appPreference.setDaily(isDaily);
                    alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                            "7:00", getResources().getString(R.string.msg_daily_reminder));
                }else{
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
                }
                break;
            case R.id.switch_today:
                isToday = todaySwitch.isChecked();
                if(isToday){
                    todaySwitch.setChecked(true);
                    appPreference.setToday(isToday);
                    alarmReceiver.setTodayAlarm(this, AlarmReceiver.TYPE_TODAY,
                            "12:15", getResources().getString(R.string.msg_today_reminder));
                }else{
                    todaySwitch.setChecked(false);
                    appPreference.setToday(isToday);
                    alarmReceiver.cancelTodayAlarm(this, AlarmReceiver.TYPE_TODAY);
                }
                break;
            case R.id.language_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }
}
