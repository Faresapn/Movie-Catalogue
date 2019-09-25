package com.example.submisi5.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.submisi5.R;


/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.example.submisi5.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.submisi5.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.item_widgets);
        views.setRemoteAdapter(R.id.stack,intent);
        views.setEmptyView(R.id.stack,R.id.none);

        Intent toastIntent = new Intent(context, Widget.class);
        toastIntent.setAction(Widget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int ViewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, R.string.touch + ViewIndex, Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onDisabled(Context context) {

    }
    @Override
    public void onEnabled(Context context) {

    }
}

