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

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.submisi5.Activity.MainActivity;
import com.example.submisi5.R;


import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class daily_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showdaily(context);
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
                .setContentText(context.getString(R.string.content_notif))
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

        Intent intent = new Intent(context, daily_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void daily_setcancel(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,daily_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

    }
}
