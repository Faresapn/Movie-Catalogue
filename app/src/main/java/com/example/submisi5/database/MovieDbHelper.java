package com.example.submisi5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 30/01/18.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovietv";

    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE)",
            DbContract.TABLE_MOVIE,
            DbContract.MovieEntry._ID        ,
            DbContract.MovieEntry.COLUMN_JUDUL     ,
            DbContract.MovieEntry.COLUMN_POSTER    ,
            DbContract.MovieEntry.COLUMN_OVERVIEW  ,
            DbContract.MovieEntry.COLUMN_RELEASE  ,
            DbContract.MovieEntry.COLUMN_RATING,
            DbContract.MovieEntry.COLUMN_RATINGBAR

            );
    private static final String SQL_CREATE_TABLE_TV= String.format("CREATE TABLE %s"
                    +"(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE,"+
                    "%s TEXT NOT NULL UNIQUE)",
            DbTvContract.TABLE_TV,
            DbTvContract.TvEntry._ID        ,
            DbTvContract.TvEntry.COLUMN_JUDUL     ,
            DbTvContract.TvEntry.COLUMN_POSTER    ,
            DbTvContract.TvEntry.COLUMN_OVERVIEW  ,
            DbTvContract.TvEntry.COLUMN_RELEASE  ,
            DbTvContract.TvEntry.COLUMN_RATING,
            DbTvContract.TvEntry.COLUMN_RATINGBAR

    );
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DbTvContract.TABLE_TV);
        onCreate(db);
    }


}