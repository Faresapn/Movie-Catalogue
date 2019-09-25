package com.example.submisi5.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submisi5.R;
import com.example.submisi5.model.Items.ItemsWidget;


import java.util.concurrent.ExecutionException;

import static com.example.submisi5.database.DbContract.MovieEntry.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

   private Context mContext;
   private Cursor mCursor;
   int ID;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        ID  =intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        if (mCursor!=null){
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);

    }

    @Override
    public void onDataSetChanged() {


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }



    private ItemsWidget getItems(int position){
        if (!mCursor.moveToPosition(position)){
            try {
                throw new IllegalAccessException("Error");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new ItemsWidget(mCursor);
    }
    @Override
    public android.widget.RemoteViews getViewAt(int i) {
        final android.widget.RemoteViews remoteViews= new android.widget.RemoteViews(mContext.getPackageName(), R.layout.widget_items);
        ItemsWidget movieTvItems = getItems(i);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext).asBitmap().load("https://image.tmdb.org/t/p/w500"+movieTvItems.getPhoto()).apply(new RequestOptions().fitCenter()).submit().get();
        }catch (InterruptedException|ExecutionException e  ){
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.image_widget,bitmap);

        Bundle extras = new Bundle();
        extras.putInt(Widget.EXTRA_ITEM,i);
        Intent intent = new Intent();
        intent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.image_widget,intent);
        return remoteViews;
    }

    @Override
    public android.widget.RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
