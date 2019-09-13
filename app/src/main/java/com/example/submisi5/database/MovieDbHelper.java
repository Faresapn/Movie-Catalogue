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
            DbContract.TABLE_TV,
            DbContract.TvEntry._ID        ,
            DbContract.TvEntry.COLUMN_JUDUL     ,
            DbContract.TvEntry.COLUMN_POSTER    ,

            DbContract.TvEntry.COLUMN_OVERVIEW  ,
            DbContract.TvEntry.COLUMN_RELEASE  ,
            DbContract.TvEntry.COLUMN_RATING,
            DbContract.TvEntry.COLUMN_RATINGBAR

    );
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }
    //bertanggung jwb untuk memastikan skema database selalu deperbaharui
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_TV);
        onCreate(db);
    }


}