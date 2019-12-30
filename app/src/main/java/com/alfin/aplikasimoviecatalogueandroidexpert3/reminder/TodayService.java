package com.alfin.aplikasimoviecatalogueandroidexpert3.reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.alfin.aplikasimoviecatalogueandroidexpert3.BuildConfig.TMDB_API_KEY;

public class TodayService extends Service{
    private static final String TAG = "TodayService";

    public void startNotificationListener() {
        Toast.makeText(this, getResources().getString(R.string.msg_today_reminder), Toast.LENGTH_LONG).show();

        //start's a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //fetching notifications from server
                //if there is notifications then call this method
                getCurrentWeather();
//                ShowNotification("Title","Description");
            }
        }).start();
    }

    @Override
    public void onCreate()
    {
        startNotificationListener();
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void ShowNotification(ArrayList<Movie> arrayList, int notifId)
    {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "DailyRelease channe2";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(getResources().getString(R.string.msg_today_reminder));
        for(Movie movie: arrayList){
            inboxStyle.addLine(movie.getJudul());
        }
        inboxStyle.setSummaryText("Total: "+arrayList.size() + getResources().getString(R.string.tab_movie));

        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notification = new NotificationCompat.Builder(getBaseContext(),CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black)
                    .setContentTitle(getResources().getString(R.string.msg_today_reminder))
                    .setContentText(getResources().getString(R.string.list_today_reminder))
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .setStyle(inboxStyle)
                    .setChannelId(CHANNEL_ID)
                    .setVibrate(new long[]{1000, 1000, 1000})
                    .build();
            notificationManager.createNotificationChannel(channel);
        }else{
            notification = new NotificationCompat.Builder(getBaseContext(),CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black)
                    .setContentTitle(getResources().getString(R.string.msg_today_reminder))
                    .setContentText(getResources().getString(R.string.list_today_reminder))
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .setStyle(inboxStyle)
                    .setVibrate(new long[]{1000, 1000, 1000})
                    .build();
        }

        notificationManager.notify(notifId, notification);
    }

    private void getCurrentWeather(){
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                //Code that uses AsyncHttpClient
                Date date = new Date();
                Timestamp ts=new Timestamp(date.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                Log.d(TAG, "Running");
                AsyncHttpClient client = new AsyncHttpClient();
                //Test tanggal 26-12-2019
//                String url = "http://api.themoviedb.org/3/discover/movie?api_key=" + TMDB_API_KEY
//                        + "&primary_release_date.gte=2019-12-26&primary_release_date.lte=2019-12-26";

                String url = "http://api.themoviedb.org/3/discover/movie?api_key=" + TMDB_API_KEY
                        + "&primary_release_date.gte=" + formatter.format(ts) + "&primary_release_date.lte=" + formatter.format(ts);
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        try {
                            int notifId = 200;
                            ArrayList<Movie> movieArrayList = new ArrayList<>();
                            String result = new String(responseBody);
                            JSONObject responseObject = new JSONObject(result);
                            JSONArray list = responseObject.getJSONArray("results");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movieItems = new Movie(movie);
                                if(movieItems.getDeskripsi().equals("")){
                                    movieItems.setDeskripsi("-");
                                }
                                movieArrayList.add(movieItems);
                            }
                            ShowNotification( movieArrayList, notifId);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        // ketika proses gagal, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                    }
                });
            }
        };
        mainHandler.post(myRunnable);

    }
}
