package com.example.submisi5.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import com.example.submisi5.database.MovieHelper;
import com.example.submisi5.database.TvHelper;

import java.util.Objects;

import static com.example.submisi5.database.DbContract.AUTHORITY;
import static com.example.submisi5.database.DbContract.MovieEntry.CONTENT_URI;
import static com.example.submisi5.database.DbContract.TABLE_MOVIE;
import static com.example.submisi5.database.DbTvContract.TABLE_TV;
import static com.example.submisi5.database.DbTvContract.TvEntry.CONTENT_URI_TV;


public class Provider extends ContentProvider {
    private static final  int MOVIE = 1;
    private static final  int MOVIE_ID = 2;

    static final int SHOW = 3;
    static final int SHOW_ID=4;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    TvHelper tvHelper;
    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE,MOVIE);
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE + "/#",MOVIE_ID);
    }

    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_TV,SHOW);
        sUriMatcher.addURI(AUTHORITY,TABLE_TV + "/#",SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();
        return true;
    }
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case SHOW:
                cursor = tvHelper.queryProvider();
                break;
            case SHOW_ID:
                cursor=tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }
        if (cursor!=null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){

        }
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long added;
        Uri mUri = null;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                if (added>0){
                   mUri = ContentUris.withAppendedId(CONTENT_URI,added);
                }
                break;
            case SHOW:

                added = tvHelper.insertProvider(contentValues);
                if (added>0){
                    mUri = ContentUris.withAppendedId(CONTENT_URI_TV,added);
                }
                default:
                    added=0;
                    break;
        }
        if (added > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }
        return mUri;
    }


    @Override
    public int delete(Uri uri, String s, String[] strings) {

        int deleted ;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:

                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case SHOW_ID:

                deleted=tvHelper.deleteProvider(uri.getLastPathSegment());
                default:
                    deleted = 0;
                    break;
        }
      if (deleted > 0 ){
          Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

      }
        return deleted;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) { @NonNull
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            case SHOW_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(),contentValues);
            default:
                updated = 0;
                break;
        }
        if (updated>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        }

        return updated;
    }
}
