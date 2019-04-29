package com.yunisrajab.rocketleague.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunisrajab.rocketleague.Adapters.NewsAdapter;
import com.yunisrajab.rocketleague.Objects.Tile;
import com.yunisrajab.rocketleague.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    String TAG = "RL";
    NewsAdapter mAdapter;
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
        mAdapter = new NewsAdapter(getActivity(), mTileArrayList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
