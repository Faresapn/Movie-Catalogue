package com.example.submisi5.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submisi5.Activity.DetailActivity;
import com.example.submisi5.R;
import com.example.submisi5.adapter.Adapter;
import com.example.submisi5.database.MovieHelper;
import com.example.submisi5.model.Items.Items;
import com.example.submisi5.model.LoadCallback;
import com.example.submisi5.model.movie.MovieVM;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submisi5.Activity.DetailActivity.EXTRA_DETAIL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorit_FilmFragment extends Fragment implements LoadCallback, Adapter.OnItemClickListener {
    Adapter adapter;
    ProgressBar mProgressBar;
    MovieHelper mMovieHelper;
    ArrayList<Items>mListFav = new ArrayList<>();
    public Favorit_FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorit__film, container, false);

        mProgressBar = v.findViewById(R.id.loading_film);
        RecyclerView mRecyclerView = v.findViewById(R.id.rv_movie);

        mMovieHelper = MovieHelper.getInstance(getContext());
        mMovieHelper.open();


        adapter = new Adapter(getContext());
        adapter.SetOnItemClickListener(Favorit_FilmFragment.this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);

        new LoadMovieAsync(mMovieHelper,this).execute();
        return v;
    }


    @Override
    public void preExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void postExecute(ArrayList<Items> items) {
        mProgressBar.setVisibility(View.GONE);
        adapter.setmItems(items);
        mListFav.addAll(items);
    }



    @Override
    public void onItemClick(int position) {
        Items movieTv_items = new Items();
        String type = "MOVIE";
        movieTv_items.setId(mListFav.get(position).getId());
        Log.d("idfav", String.valueOf(mListFav.get(position).getId()));
        movieTv_items.setPhoto(mListFav.get(position).getPhoto());
        movieTv_items.setTitle_film(mListFav.get(position).getTitle_film());
        movieTv_items.setDesc_film(mListFav.get(position).getInfo_film());
        movieTv_items.setInfo_film(mListFav.get(position).getDesc_film());
        movieTv_items.setRate(mListFav.get(position).getRate());
        movieTv_items.setRating_bar(mListFav.get(position).getRating_bar());
        movieTv_items.setType(type);
        Intent detail = new Intent(getContext(), DetailActivity.class);

        detail.putExtra(EXTRA_DETAIL,movieTv_items);
        startActivity(detail);
    }

    private class LoadMovieAsync  extends AsyncTask<Void, Void, ArrayList<Items>> {
        WeakReference<MovieHelper> movieHelperWeakReference;
        WeakReference<LoadCallback>loadCallbackWeakReference;
        public LoadMovieAsync(MovieHelper mMovieHelper, LoadCallback context) {
            movieHelperWeakReference = new WeakReference<>(mMovieHelper);
            loadCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Items> movieTvItems) {
            super.onPostExecute(movieTvItems);
            loadCallbackWeakReference.get().postExecute(movieTvItems);
        }

        @Override
        protected ArrayList<Items> doInBackground(Void... voids) {
            return movieHelperWeakReference.get().getAllFilm();
        }
    }

}
