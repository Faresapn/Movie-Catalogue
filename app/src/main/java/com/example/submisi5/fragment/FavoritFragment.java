package com.example.submisi5.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submisi5.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {


    ViewPager viewpager;

    TabLayout tabss;
    public FavoritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_favorit, container, false);
        tabss = v.findViewById(R.id.viewpagertab);
        viewpager = v.findViewById(R.id.viewpager);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        viewpager.setAdapter(new viewpageradapter(getFragmentManager(),tabss.getTabCount()));
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabss));
        //tabss.setupWithViewPager(viewpager);
        tabss.setTabMode(TabLayout.MODE_FIXED);
        tabss.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }
    public class viewpageradapter extends FragmentStatePagerAdapter {
        int mNumofTabs;
        public viewpageradapter(FragmentManager fm, int mNumOfTabs) {
            super(fm);
            this.mNumofTabs = mNumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:

                    return new Favorit_FilmFragment();
                case 1:
                    return new Favorit_ShowFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumofTabs;
        }
    }

}
