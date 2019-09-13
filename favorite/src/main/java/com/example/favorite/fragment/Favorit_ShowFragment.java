package com.example.favorite.fragment;


import android.annotation.SuppressLint;

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
import com.example.favorite.adapter.TVFavAdapter;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.favorite.DbContract.TvEntry.CONTENT_URI_TV;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorit_ShowFragment extends Fragment  {

    TVFavAdapter tvFavAdapter;
    Cursor mList;
    RecyclerView mRecyclerView;


    public Favorit_ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =   inflater.inflate(R.layout.fragment_favorit__show, container, false);

        mRecyclerView = v.findViewById(R.id.rv_show);

        tvFavAdapter = new TVFavAdapter(getContext());
        Log.d("tvcreate","tvcreate");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(tvFavAdapter);
        new LoadTvFav().execute();
        return v;
    }


    private class LoadTvFav extends AsyncTask<Void,Void,Cursor> {
        private Cursor mcursor;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d("background", String.valueOf(CONTENT_URI_TV));
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI_TV,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mcursor = cursor;
            Log.d("cursor", String.valueOf(mcursor));
            tvFavAdapter.setmItems(mcursor);
            tvFavAdapter.notifyDataSetChanged();
        }


    }
}
