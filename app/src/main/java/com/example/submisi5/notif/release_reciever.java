package com.example.submisi5.notif;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class release_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      ////  NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        getData(context);
    }

    void getData(final Context context){
        RequestQueue mRequestQueue= Volley.newRequestQueue(context);
        Calendar rightnow = Calendar.getInstance();
        String year = String.valueOf(rightnow.get(Calendar.YEAR));
        String month = String.valueOf(rightnow.get(Calendar.MONTH)+1);
        String day = String.valueOf(rightnow.get(Calendar.DATE));
        String release_date = year+"-"+month+"-"+day;
        Log.d("RELEASE",release_date);
        String url = context.getResources().getString(R.string.api_release);
        Log.d("URL_RELEASE",url);

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(mRequest);

    }

    void showrelease(Context context,String title,int i){
        Intent repeating = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,i,repeating,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_black_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(title)
                .setAutoCancel(true);
        Notification notification = mBuilder.build();

        // if (mNotificationManager !=null){
        if (mNotificationManager != null) {
            mNotificationManager.notify(i,notification);
        }
    }
    public static void release_setalarmmanager(Context context){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);


            Intent intent = new Intent(context, release_reciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void release_setcancel(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,release_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

    }
}
