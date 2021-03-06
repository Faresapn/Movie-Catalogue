package com.example.submisi5.fragment;



import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submisi5.Activity.DetailActivity;
import com.example.submisi5.R;
import com.example.submisi5.Activity.SearchMovieTv;
import com.example.submisi5.adapter.Adapter;
import com.example.submisi5.model.Items.Items;
import com.example.submisi5.model.movie.MovieVM;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.submisi5.Activity.DetailActivity.EXTRA_DETAIL;
import static com.example.submisi5.Activity.SearchMovieTv.EXTRA_SEARCH;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements Adapter.OnItemClickListener {

    private Adapter adapter;
    private MovieVM movieVM;
    private ProgressBar mProgressBar;
    public MovieFragment() {

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        setHasOptionsMenu(true);
        mProgressBar = v.findViewById(R.id.loading_film);
        adapter = new Adapter(getContext());
        adapter.SetOnItemClickListener(MovieFragment.this);
        adapter.notifyDataSetChanged();
        movieVM = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieVM.class);
        movieVM.getShow().observe(MovieFragment.this, getmMovieTvItems);
        movieVM.getAPI();
        RecyclerView recyclerView = v.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return v;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        if(searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    Items items = new Items();
                    items.setTitle_film(query);
                    items.setType("MOVIE");
                    Intent intent = new Intent(getContext(), SearchMovieTv.class);
                    intent.putExtra(EXTRA_SEARCH,items);
                    startActivity(intent);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });

        }

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



    @Override
    public void onItemClick(int i) {
        Items items = new Items();
        String type = "MOVIE";
        items.setPhoto(movieVM.mitems.get(i).getPhoto());
        items.setTitle_film(movieVM.mitems.get(i).getTitle_film());
        items.setDesc_film(movieVM.mitems.get(i).getDesc_film());
        items.setInfo_film(movieVM.mitems.get(i).getInfo_film());
        items.setRate(movieVM.mitems.get(i).getRate());
        items.setRating_bar(movieVM.mitems.get(i).getRating_bar());
        items.setType(type);
        Intent detail = new Intent(getContext(), DetailActivity.class);
        detail.putExtra(EXTRA_DETAIL, items);
        startActivity(detail);

    }

}
