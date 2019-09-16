package com.example.submisi5;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submisi5.adapter.Adapter;
import com.example.submisi5.fragment.MovieFragment;
import com.example.submisi5.model.Items.Items;
import com.example.submisi5.model.movie.MovieVM;
import com.example.submisi5.model.tv.ShowVM;

import java.util.ArrayList;


public class SearchMovieTv extends AppCompatActivity implements Adapter.OnItemClickListener {
    public static final String EXTRA_SEARCH="extra_detail";
    Adapter adapter;
    MovieVM movieVM;
    ShowVM showVM;
    ProgressBar mProgressBar;
    RecyclerView mRecyclerView;
    Items movieTvItems;
    String query,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_tv);
        settoolbar();
        setintentdetail();
        setsearchmovietv(query);
    }
    void settoolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
    void setsearchmovietv(String title){
        mProgressBar = findViewById(R.id.progress_search);
        mProgressBar.setVisibility(View.VISIBLE);

        adapter = new Adapter(SearchMovieTv.this);
        adapter.SetOnItemClickListener(SearchMovieTv.this);
        adapter.notifyDataSetChanged();
        if (type.equals("MOVIE")){
            movieVM = ViewModelProviders.of(SearchMovieTv.this).get(MovieVM.class);
            movieVM.getShow().observe(SearchMovieTv.this,getmMovieTvItems);
            movieVM.searchmovie(title);
        }else if (type.equals("TV")){
            showVM = ViewModelProviders.of(SearchMovieTv.this).get(ShowVM.class);
            showVM.getmTvItems().observe(SearchMovieTv.this,getmTvItems);
            showVM.searchtv(title);
        }


        mRecyclerView = findViewById(R.id.recycler_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchMovieTv.this,RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);
    }
    void setintentdetail(){
        movieTvItems = new Items();
        movieTvItems = getIntent().getParcelableExtra(EXTRA_SEARCH);
        query = movieTvItems.getTitle_film();
        type = movieTvItems.getType();
    }
    private Observer<? super ArrayList<Items>> getmMovieTvItems = new Observer<ArrayList<Items>>() {
        @Override
        public void onChanged(ArrayList<Items> movieTvItems) {
            if (movieTvItems!=null){
                adapter.setmItems(movieTvItems);
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };
    private Observer<? super ArrayList<Items>> getmTvItems = new Observer<ArrayList<Items>>() {
        @Override
        public void onChanged(ArrayList<Items> movieTvItems) {
            if (movieTvItems!=null){
                adapter.setmItems(movieTvItems);
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };


    @Override
    public void onItemClick(int position) {

    }
}
