package com.yunisrajab.rocketleague.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yunisrajab.rocketleague.Activities.MainActivity;
import com.yunisrajab.rocketleague.Objects.Tourney;
import com.yunisrajab.rocketleague.R;

import java.util.ArrayList;


public class TabAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater  mLayoutInflater;
    ArrayList<Tourney>    mainList;
    ArrayList<Tourney>    curatedList;
    String TAG = "Curator tabAdapter";
    RecyclerView    mRecyclerView;
    TourneyAdapter mAdapter;

    public TabAdapter(Context context, ArrayList<Tourney> mainList,  ArrayList<Tourney>    curatedList)    {
        mContext    =   context;
        this.mainList   =   mainList;
        this.curatedList    =   curatedList;
    }
    //TODO  going back to main after switching causes crash
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater =   (LayoutInflater)    mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View    view    =   mLayoutInflater.inflate(R.layout.tab,   container,  false);

        Log.e(TAG,  "position "+position);
        LinearLayout    tabLayout   =   (LinearLayout)  view.findViewById(R.id.tabLinearLayout);
        mRecyclerView    =   view.findViewById(R.id.tourneyList);

        if (mainList  ==  null)   mainList  =   new ArrayList<>();
        if (curatedList  ==  null)   curatedList  =   new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(mAdapter);

        switch (position)   {
            case    0:
                mAdapter = new TourneyAdapter(mContext,    curatedList,   "all");
                break;
            case    1:
                mAdapter = new TourneyAdapter(mContext,    curatedList,   "all");
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view    ==  o);
    }
}
