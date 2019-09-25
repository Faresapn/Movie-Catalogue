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
import com.example.favorite.adapter.ShowAdapter;

import java.util.Objects;

import static com.example.favorite.DbTvContract.TvEntry.CONTENT_URI_TV;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorit_ShowFragment extends Fragment  {

    ShowAdapter showAdapter;
    Cursor mCursor;
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
        showAdapter = new ShowAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(showAdapter);
        new LoadTvFav().execute();
        return v;
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadTvFav extends AsyncTask<Void,Void,Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext()).getContentResolver().query(CONTENT_URI_TV,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mCursor = cursor;
            showAdapter.setmMovieTvItems(mCursor);
            showAdapter.notifyDataSetChanged();
        }


    }
}
