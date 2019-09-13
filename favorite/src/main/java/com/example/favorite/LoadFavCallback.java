package com.example.favorite;

import android.database.Cursor;


public interface LoadFavCallback {
    void postExecute(Cursor mMovieTvItems);
}
