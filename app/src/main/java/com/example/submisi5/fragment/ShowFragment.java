package com.example.submisi5.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.example.submisi5.adapter.Adapter;
import com.example.submisi5.model.Items.Items;
import com.example.submisi5.model.tv.ShowVM;

import java.util.ArrayList;

import static com.example.submisi5.Activity.DetailActivity.EXTRA_DETAIL;


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

        mProgressBar = v.findViewById(R.id.loading_show);

        adapter = new Adapter(getContext());
        adapter.setOnItemClickListener(ShowFragment.this);
        adapter.notifyDataSetChanged();

        showVM= ViewModelProviders.of(getActivity()).get(ShowVM.class);
        showVM.getShow().observe(ShowFragment.this,getShow);
        showVM.getAPI();

        RecyclerView mRecyclerView = v.findViewById(R.id.rv_show);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);

        return v;


    }
    private Observer<ArrayList<Items>> getShow = new Observer<ArrayList<Items>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Items> items) {
            if (items != null) {
                adapter.setmItems(items);
                showLoading(false);
            }
        }
    };
    private void showLoading(Boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }



    @Override
    public void onItemClick(int i) {
        Items items = new Items();
        String type = "TV";
        items.setPhoto(ShowVM.mitems.get(i).getPhoto());

        items.setTitle_film(ShowVM.mitems.get(i).getTitle_film());
        items.setDesc_film(ShowVM.mitems.get(i).getDesc_film());
        items.setInfo_film(ShowVM.mitems.get(i).getInfo_film());
        items.setRate(ShowVM.mitems.get(i).getRate());
        items.setRating_bar(ShowVM.mitems.get(i).getRating_bar());
        items.setType(type);

        Intent detail = new Intent(getContext(), DetailActivity.class);

        detail.putExtra(EXTRA_DETAIL,items);
        startActivity(detail);
    }
}
