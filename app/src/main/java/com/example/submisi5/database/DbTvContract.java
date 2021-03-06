package com.example.submisi5.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbTvContract {
    public static String TABLE_TV = "TV";
    public static final String AUTHORITY="com.example.submisi5";
    static final String SCHEME = "content";
    public static final class TvEntry implements BaseColumns {

        public static String COLUMN_JUDUL =   "original_name";
        public static String COLUMN_POSTER =    "poster_path"  ;
        public static String COLUMN_OVERVIEW = "overview";
        public static String COLUMN_RELEASE =  "first_air_date";
        public static String COLUMN_RATING =  "vote_average";
        public static String COLUMN_RATINGBAR =  "vote";
        public static final Uri CONTENT_URI_TV = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();

    }

}
