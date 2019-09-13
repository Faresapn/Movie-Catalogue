package com.example.favorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DbContract {

    public static final String AUTHORITY="com.example.submisi5";
    static final String SCHEME = "content";

    public static final class MovieEntry implements BaseColumns {
        public static String COLUMN_JUDUL    = "title";
        public static String COLUMN_POSTER   ="poster_path";
        public static String COLUMN_OVERVIEW = "overview";
        public static String COLUMN_RELEASE  ="release_date";
        public static String COLUMN_RATING   ="vote_average";
        public static String COLUMN_RATINGBAR   ="vote";
        static final String TABLE_MOVIE= "MOVIE";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();

    }


    public static final class TvEntry implements BaseColumns {

        static String COLUMN_JUDUL =   "original_name";
        static String COLUMN_POSTER =    "poster_path"  ;
        static String COLUMN_OVERVIEW = "overview";
        static String COLUMN_RELEASE =  "first_air_date";
        static String COLUMN_RATING =  "vote_average";
        static String COLUMN_RATINGBAR =  "vote";
        public static String TABLE_TV = "TV";
        public static final Uri CONTENT_URI_TV = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();


    }

    public static String getColoumnString(Cursor cursor, String coloumn_name){
        return cursor.getString(cursor.getColumnIndex(coloumn_name));
    }
    public static int getColoumnInt(Cursor cursor,String coloumn_name){
        return cursor.getInt(cursor.getColumnIndex(coloumn_name));
    }
}
