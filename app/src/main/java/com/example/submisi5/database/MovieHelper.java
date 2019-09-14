package com.example.submisi5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.submisi5.model.Items.Items;

import java.util.ArrayList;


import static com.example.submisi5.database.DbContract.TABLE_MOVIE;


public class MovieHelper {

    //2 membuat Uri Matcher
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static MovieDbHelper moviedb;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;


    public MovieHelper(Context context) {

        moviedb = new MovieDbHelper(context);
        database = moviedb.getWritableDatabase();
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = moviedb.getWritableDatabase();
        database= moviedb.getReadableDatabase();
    }

    public void close() {
        moviedb.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public ArrayList<Items> getAllFilm(){
        ArrayList<Items> movieTvItems = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null, DbContract.MovieEntry._ID ,null);
        cursor.moveToFirst();
        Items items;
        if (cursor.getCount() > 0 ){
            do {
                items = new Items();
                items.setId        (cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.MovieEntry._ID)));
                items.setTitle_film(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_JUDUL)));
                items.setDesc_film (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_RELEASE)));
                items.setPhoto     (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_POSTER)));
                items.setInfo_film (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_OVERVIEW)));
                items.setRate      (cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_RATING)));
                items.setRating_bar(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MovieEntry.COLUMN_RATINGBAR)));
                Log.d("idhelper", String.valueOf(Integer.valueOf(cursor.getInt(0))));
                movieTvItems.add(items);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieTvItems;
    }
    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " +DbContract.MovieEntry.COLUMN_JUDUL+ " " + " LIKE " +"'"+name+"'" ;
//        Cursor cursor = database.query(DATABASE_TABLE,null,"'"+name+"'",null,null,null,null ,null);
        Cursor cursor = database.rawQuery(querySingleRecord,null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0 ){

            return true;
        }else if(cursor.getCount() == 0){
            return false;
        }
//        cursor.close();
        return false;
    }

    public long insertMovie(Items mMovieTvItems){
        ContentValues args = new ContentValues();
       // args.put(DbContract.MovieEntry._ID,mMovieTvItems.getId());
        args.put(DbContract.MovieEntry.COLUMN_JUDUL,mMovieTvItems.getTitle_film());
        args.put(DbContract.MovieEntry.COLUMN_OVERVIEW,mMovieTvItems.getInfo_film());
        args.put(DbContract.MovieEntry.COLUMN_POSTER,mMovieTvItems.getPhoto());
        args.put(DbContract.MovieEntry.COLUMN_RELEASE,mMovieTvItems.getDesc_film());
        args.put(DbContract.MovieEntry.COLUMN_RATING,mMovieTvItems.getRate());
        args.put(DbContract.MovieEntry.COLUMN_RATINGBAR,mMovieTvItems.getRating_bar());
        Log.d("helper",mMovieTvItems.getDesc_film());
        return database.insert(DATABASE_TABLE,null,args);
    }

    public int deleteMovie(String title){
        return database.delete(TABLE_MOVIE, DbContract.MovieEntry.COLUMN_JUDUL+ " = " + "'"+title+"'" , null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null,DbContract.MovieEntry._ID,new String[]{id},null,null,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,null,null,null,null,null,null);
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,DbContract.MovieEntry._ID + " = ?",new String[]{id});
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,DbContract.MovieEntry._ID+ " = ?",new String[]{id});
    }



}
