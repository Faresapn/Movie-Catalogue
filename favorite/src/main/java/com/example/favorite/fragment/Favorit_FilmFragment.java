package com.example.favorite.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favorite.R;
import com.example.favorite.adapter.MovieTvFavAdapter;

import java.lang.ref.WeakReference;
import java.util.Objects;

import static com.example.favorite.DbContract.MovieEntry.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorit_FilmFragment extends Fragment  {
    MovieTvFavAdapter mMovieTvFavAdapter;
    Cursor mList;
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
        Log.d("creattemovie","oncreate");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(mMovieTvFavAdapter);
        new LoadMovieFav().execute();
        return v;
    }

    @SuppressLint("StaticFieldLeak")
    public class LoadMovieFav extends AsyncTask<Void,Void,Cursor>{
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
            mList = cursor;
            mMovieTvFavAdapter.setmMovieTvItems(mList);
            mMovieTvFavAdapter.notifyDataSetChanged();

        }
    }

}
