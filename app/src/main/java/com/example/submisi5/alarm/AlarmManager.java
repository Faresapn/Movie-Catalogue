package com.example.submisi5.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.submisi5.Activity.MainActivity;
import com.example.submisi5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class AlarmManager extends BroadcastReceiver {
    public static final String IS_DAILY_REMINDER="IS_DAILY_REMINDER";
    public static final String IS_RELEASE_REMINDER="IS_RELEASE_REMINDER";
    @Override
    public void onReceive(Context context, Intent intent) {
        showdaily(context);
        getData(context);
    }
    void showdaily(Context context){
        Intent repeating = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,101,repeating,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"channel_01")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_movie))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getString(R.string.daili))
                .setAutoCancel(true);
        Notification notification = mBuilder.build();


        if (mNotificationManager != null) {
            mNotificationManager.notify(101,notification);
        }
    }
    public void daily_setalarmmanager(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Intent intent = new Intent(context, AlarmManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY,pendingIntent);
        }

    }

    public void daily_setcancel(Context context){
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

    }

    void getData(final Context context){
        RequestQueue mRequestQueue= Volley.newRequestQueue(context);
        Calendar rightnow = Calendar.getInstance();
        String year = String.valueOf(rightnow.get(Calendar.YEAR));
        String month = String.valueOf(rightnow.get(Calendar.MONTH)+1);
        String day = String.valueOf(rightnow.get(Calendar.DATE));
        String release_date = year+"-"+month+"-"+day;
        Log.d("RELEASE",release_date);
        String url = context.getResources().getString(R.string.api_release,release_date,release_date);
        Log.d("URL_RELEASE",url);

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");
                int length = jsonArray.length();
                for(int i = 0;i<length;i++){
                    JSONObject result = jsonArray.getJSONObject(i);
                    String title = result.getString("title");
                    showrelease(context,title,i);
                }

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }, Throwable::printStackTrace);
        mRequestQueue.add(mRequest);

    }

    void showrelease(Context context,String title,int i){
        Intent repeating = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,repeating,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_movie))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(title)
                .setAutoCancel(true);
        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(i,notification);
        }
    }
    public void release_setalarmmanager(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);


        Intent intent = new Intent(context, AlarmManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY,pendingIntent);
        }

    }

    public void release_setcancel(Context context){
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

    }
}
