package com.example.submisi5.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;

import android.net.Uri;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submisi5.database.DbContract;
import com.example.submisi5.database.MovieHelper;
import com.example.submisi5.database.TvHelper;


import java.util.Objects;

import static android.provider.SettingsSlicesContract.AUTHORITY;
import static com.example.submisi5.database.DbContract.MovieEntry.CONTENT_URI;
import static com.example.submisi5.database.DbContract.TABLE_MOVIE;
import static com.example.submisi5.database.DbContract.TABLE_TV;
import static com.example.submisi5.database.DbContract.TvEntry.CONTENT_URI_TV;

public class Provider extends ContentProvider {
    public static final  int MOVIE = 1;
    public static final  int MOVIE_ID = 2;

    public static final int TV = 10;
    public static final int TV_ID=11;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    TvHelper tvHelper;
    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE,MOVIE);
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE + "/#",MOVIE_ID);
    }

    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_TV,TV);
        sUriMatcher.addURI(AUTHORITY,TABLE_TV + "/#",TV_ID);
    }
    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();
        return true;
    }


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
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
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
            case TV:

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
            case TV_ID:

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


    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            case TV_ID:
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