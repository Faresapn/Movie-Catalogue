package com.example.favorite.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favorite.DetailActivity;
import com.example.favorite.Items;
import com.example.favorite.R;
import com.example.favorite.adapter.MovieTvFavAdapter;
import com.example.favorite.adapter.TVFavAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.favorite.DbContract.MovieEntry.CONTENT_URI;
import static com.example.favorite.DbContract.TvEntry.CONTENT_URI_TV;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorit_FilmFragment extends Fragment  {
    MovieTvFavAdapter mMovieTvFavAdapter;

    RecyclerView mRecyclerView;
    public Favorit_FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorit__film, container, false);
        mRecyclerView = v.findViewById(R.id.rv_movie);

        mMovieTvFavAdapter = new MovieTvFavAdapter(getContext());
        Log.d("creattemovie", String.valueOf(mMovieTvFavAdapter));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mMovieTvFavAdapter);
        new LoadMovieFav().execute();
        return v;
    }

    @SuppressLint("StaticFieldLeak")
    public class LoadMovieFav extends AsyncTask<Void,Void,Cursor>{
        private Cursor mcursor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d("loadmovie", String.valueOf(CONTENT_URI));
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mcursor = cursor;

            mMovieTvFavAdapter.setmMovieTvItems(mcursor);
            Log.d("cursor", String.valueOf(mcursor));
            mMovieTvFavAdapter.notifyDataSetChanged();

        }
    }



}
