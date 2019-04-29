package com.yunisrajab.rocketleague.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunisrajab.rocketleague.Adapters.TabAdapter;
import com.yunisrajab.rocketleague.Objects.Tourney;
import com.yunisrajab.rocketleague.R;

import java.util.ArrayList;

public class TourneyFragment extends Fragment {

    String TAG = "RL";
    ArrayList<Tourney> mTourneyArrayList;
    View    rootView;
    ViewPager   mViewPager;
    TabAdapter mTabAdapter;

    public TourneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        mTourneyArrayList  =   (ArrayList<Tourney>)  args.getSerializable("arraylist");
    }

    public ArrayList<Tourney> getArrayList() {
        return mTourneyArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView    =   inflater.inflate(R.layout.fragment_tourneys,    container,  false);

        TabLayout tabLayout   =   rootView.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Major"));
        tabLayout.addTab(tabLayout.newTab().setText("Minor"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(getActivity().getColor(R.color.colorPrimaryDark),
                getActivity().getColor(R.color.colorWhite));

        mViewPager  =   rootView.findViewById(R.id.viewPager);
        mTabAdapter =   new TabAdapter(getActivity(), mTourneyArrayList);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }
}
