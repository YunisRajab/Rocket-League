package com.yunisrajab.rocketleague.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yunisrajab.rocketleague.Adapters.TabAdapter;
import com.yunisrajab.rocketleague.Adapters.TileAdapter;
import com.yunisrajab.rocketleague.Adapters.TourneyAdapter;
import com.yunisrajab.rocketleague.Objects.Tile;
import com.yunisrajab.rocketleague.Objects.Tourney;
import com.yunisrajab.rocketleague.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    String TAG = "RL";
    TileAdapter mAdapter;
    ArrayList<Tile> mTileArrayList;
    View rootView;
    RecyclerView mRecyclerView;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        mTileArrayList  =   (ArrayList<Tile>)  args.getSerializable("arraylist");
    }

    public ArrayList<Tile> getArrayList() {
        return mTileArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView    =   inflater.inflate(R.layout.fragment_news,    container,  false);
        mRecyclerView = rootView.findViewById(R.id.newsList);

        if (mTileArrayList  ==  null)   mTileArrayList  =   new ArrayList<>();
        mAdapter = new TileAdapter(getActivity(), mTileArrayList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
