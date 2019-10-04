package com.example.submisi5.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;



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
    static final int SHOW_ID =4;


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
    @SuppressWarnings({"NullableProblems", "ConstantConditions"})
    @NonNull

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        int i = sUriMatcher.match(uri);
        if (MOVIE == i) {
            cursor = movieHelper.queryProvider();
        } else if (i == MOVIE_ID) {
            cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
        } else if (i == SHOW) {
            cursor = tvHelper.queryProvider();
        } else if (i == SHOW_ID) {
            cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
        } else {
            cursor = null;
        }
        if (cursor!=null){
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
        }

        return cursor;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String getType(Uri uri) {
        sUriMatcher.match(uri);
        return null;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long added;
        Uri mUri = null;
        int i = sUriMatcher.match(uri);
        if (MOVIE == i) {
            added = movieHelper.insertProvider(contentValues);
            if (added > 0) {
                mUri = ContentUris.withAppendedId(CONTENT_URI, added);
            }
        } else if (SHOW == i) {
            added = tvHelper.insertProvider(contentValues);
            if (added > 0) {
                mUri = ContentUris.withAppendedId(CONTENT_URI_TV, added);
            }

            added = 0;
        } else {
            added = 0;
        }
        if (added > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }
        return mUri;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public int delete(Uri uri, String s, String[] strings) {

        int deleted ;
        int i = sUriMatcher.match(uri);
        if (MOVIE_ID == i) {
            deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
        } else if (SHOW_ID == i) {
            deleted = 0;
        } else {
            deleted = 0;
        }
      if (deleted > 0 ){
          Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

      }
        return deleted;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) { @NonNull
        int updated;
        int i = sUriMatcher.match(uri);
        if (MOVIE_ID == i) {
            updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        } else if (SHOW_ID == i) {
            updated = 0;
        } else {
            updated = 0;
        }
        if (updated>0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);

        }

        return updated;
    }
}
