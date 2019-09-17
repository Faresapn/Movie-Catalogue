package com.example.submisi5.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.submisi5.Activity.DetailActivity;
import com.example.submisi5.R;
import com.example.submisi5.Activity.SearchMovieTv;
import com.example.submisi5.adapter.Adapter;
import com.example.submisi5.model.Items.Items;
import com.example.submisi5.model.tv.ShowVM;

import java.util.ArrayList;

import static com.example.submisi5.Activity.DetailActivity.EXTRA_DETAIL;
import static com.example.submisi5.Activity.SearchMovieTv.EXTRA_SEARCH;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment implements Adapter.OnItemClickListener {

    private Adapter adapter;
    private ShowVM showVM;
    private ProgressBar mProgressBar;
    public ShowFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show, container, false);
        setHasOptionsMenu(true);
        mProgressBar = v.findViewById(R.id.loading_show);

        adapter = new Adapter(getContext());
        adapter.SetOnItemClickListener(ShowFragment.this);
        adapter.notifyDataSetChanged();

        showVM = ViewModelProviders.of(getActivity()).get(ShowVM.class);
        showVM.getmTvItems().observe(ShowFragment.this, getmMovieTvItems);
        showVM.getAPI();

        RecyclerView mRecyclerView = v.findViewById(R.id.rv_show);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager sm = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (sm != null) {
            SearchView sv = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            sv.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
            sv.setQueryHint(getResources().getString(R.string.search_));
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    Log.d("tesstt", query);
                    Items items = new Items();
                    items.setTitle_film(query);
                    items.setType("MOVIE");
                    Intent intent = new Intent(getActivity(), SearchMovieTv.class);
                    intent.putExtra(EXTRA_SEARCH, items);
                    startActivity(intent);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Toast.makeText(getContext(), newText, Toast.LENGTH_SHORT).show();

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
        String type = "TV";
        items.setPhoto(showVM.mitems.get(i).getPhoto());

        items.setTitle_film(showVM.mitems.get(i).getTitle_film());
        items.setDesc_film(showVM.mitems.get(i).getDesc_film());
        items.setInfo_film(showVM.mitems.get(i).getInfo_film());
        items.setRate(showVM.mitems.get(i).getRate());
        items.setRating_bar(showVM.mitems.get(i).getRating_bar());
        items.setType(type);

        Intent detail = new Intent(getContext(), DetailActivity.class);

        detail.putExtra(EXTRA_DETAIL,items);
        startActivity(detail);
    }
}
