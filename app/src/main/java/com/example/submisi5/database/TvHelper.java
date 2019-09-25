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


import static com.example.submisi5.database.DbTvContract.TABLE_TV;


/**
 * Created by root on 31/01/18.
 */

public class TvHelper  {

    private static final String DATABASE_TABLE = TABLE_TV;
    private static MovieDbHelper databaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    public TvHelper(Context context) {
        databaseHelper = new MovieDbHelper(context);
        database = databaseHelper.getWritableDatabase();

    }

    public static TvHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        database= databaseHelper.getReadableDatabase();
    }

    public ArrayList<Items> getAllTv(){
        ArrayList<Items> movieTvItems = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null, DbTvContract.TvEntry._ID ,null);
        cursor.moveToFirst();
        Items items;
        if (cursor.getCount() > 0 ){
            do {
                items = new Items();
                items.setId        (cursor.getInt(cursor.getColumnIndexOrThrow   (DbTvContract.TvEntry._ID)));
                items.setTitle_film(cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_JUDUL)));
                items.setDesc_film (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_RELEASE)));
                items.setPhoto     (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_POSTER)));
                items.setInfo_film (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_OVERVIEW)));
                items.setRate      (cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_RATING)));
                items.setRating_bar(cursor.getString(cursor.getColumnIndexOrThrow(DbTvContract.TvEntry.COLUMN_RATINGBAR)));
                movieTvItems.add(items);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieTvItems;

    }
    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " +DbTvContract.TvEntry.COLUMN_JUDUL+ " " + " LIKE " +"'"+name+"'" ;
        Cursor cursor = database.rawQuery(querySingleRecord,null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0 ){
            return true;
        }else if(cursor.getCount() == 0){
            return false;
        }
        return false;
    }
    public long insertTv(Items items){
        ContentValues args = new ContentValues();
        args.put(DbTvContract.TvEntry.COLUMN_JUDUL,items.getTitle_film());
        args.put(DbTvContract.TvEntry.COLUMN_OVERVIEW,items.getInfo_film());
        args.put(DbTvContract.TvEntry.COLUMN_POSTER,items.getPhoto());
        args.put(DbTvContract.TvEntry.COLUMN_RELEASE,items.getDesc_film());
        args.put(DbTvContract.TvEntry.COLUMN_RATING,items.getRate());
        args.put(DbTvContract.TvEntry.COLUMN_RATINGBAR,items.getRating_bar());
        Log.d("helper",items.getInfo_film());
        return database.insert(DATABASE_TABLE,null,args);
    }

    public int deleteTv(String title){
        return database.delete(TABLE_TV, DbTvContract.TvEntry.COLUMN_JUDUL+ " = '" + title + "'", null);
    }
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null,DbTvContract.TvEntry._ID,new String[]{id},null,null,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,null,null,null,null,null,null);
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values, DbTvContract.TvEntry._ID + " = ?",new String[]{id});
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, DbTvContract.TvEntry._ID+ " = ?",new String[]{id});
    }
}

