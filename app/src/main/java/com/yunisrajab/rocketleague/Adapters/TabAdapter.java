package com.yunisrajab.rocketleague.Adapters;

import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.ScrollView;

import com.yunisrajab.rocketleague.Activities.MainActivity;
import com.yunisrajab.rocketleague.Objects.Tourney;
import com.yunisrajab.rocketleague.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class TabAdapter extends PagerAdapter {

    String TAG = "RL tabAdapter";
    Context mContext;
    LayoutInflater  mLayoutInflater;
    ArrayList<Tourney>    mTourneyArrayList;
    ArrayList<Tourney>    premierList;
    ArrayList<Tourney>    majorList;
    ArrayList<Tourney>    minorList;
    ArrayList<Tourney>    monthlyList;
    ArrayList<Tourney>    weeklyList;
    ArrayList<Tourney>    showList;
    ArrayList<Tourney>    qualifierList;
    RecyclerView premierRecyclerView;
    RecyclerView majorRecyclerView;
    RecyclerView minorRecyclerView;
    RecyclerView monthlyRecyclerView;
    RecyclerView weeklyRecyclerView;
    RecyclerView showRecyclerView;
    RecyclerView qualifierRecyclerView;
    int counter = 0;
    int tabNo = 3;

    public TabAdapter(Context context, ArrayList<Tourney> tourneyArrayList)    {
        mContext    =   context;
        mTourneyArrayList = tourneyArrayList;
        premierList = new ArrayList<>();
        majorList = new ArrayList<>();
        minorList = new ArrayList<>();
        monthlyList = new ArrayList<>();
        weeklyList = new ArrayList<>();
        showList = new ArrayList<>();
        qualifierList = new ArrayList<>();
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Premier_Tournaments");
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Major_Tournaments");
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Minor_Tournaments");
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Monthly_Tournaments");
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Weekly_Tournaments");
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Show_Matches");
//        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Qualifier_Tournaments");
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mLayoutInflater =   (LayoutInflater)    mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View    view    =   mLayoutInflater.inflate(R.layout.tab,   container,  false);

        Log.e(TAG,  "position "+position);
        separateLists();

        premierRecyclerView = view.findViewById(R.id.premierList);
        majorRecyclerView = view.findViewById(R.id.majorList);
        minorRecyclerView = view.findViewById(R.id.minorList);
        monthlyRecyclerView = view.findViewById(R.id.monthlyList);
        weeklyRecyclerView = view.findViewById(R.id.weeklyList);
        showRecyclerView = view.findViewById(R.id.showList);
        qualifierRecyclerView = view.findViewById(R.id.qualifierList);

        switch (position)   {
            case    0:
                setupRecycler(premierRecyclerView, premierList);
                setupRecycler(majorRecyclerView, majorList);
                setupRecycler(minorRecyclerView, minorList);
                setupRecycler(monthlyRecyclerView, monthlyList);
                setupRecycler(weeklyRecyclerView, weeklyList);
                setupRecycler(showRecyclerView, showList);
                setupRecycler(qualifierRecyclerView, qualifierList);
                break;
            case    1:
//                mAdapter = new TourneyAdapter(mContext,    mTourneyArrayList);
                break;
            case    2:
//                mAdapter = new TourneyAdapter(mContext,    mTourneyArrayList);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ScrollView) object);
    }

    @Override
    public int getCount() {
        return tabNo;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view    ==  o);
    }

    void setupRecycler(RecyclerView recyclerView, ArrayList<Tourney> arrayList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new TourneyAdapter(mContext,    arrayList));
    }

    void separateLists() {
        for (Tourney tourney: mTourneyArrayList) {
            if (tourney.getType().contains("Premier")) premierList.add(tourney);
            if (tourney.getType().contains("Major")) majorList.add(tourney);
            if (tourney.getType().contains("Minor")) minorList.add(tourney);
            if (tourney.getType().contains("Monthly")) monthlyList.add(tourney);
            if (tourney.getType().contains("Weekly")) weeklyList.add(tourney);
            if (tourney.getType().contains("Show")) showList.add(tourney);
            if (tourney.getType().contains("Qualifier")) qualifierList.add(tourney);
        }
    }

    private void populateLists(Elements rows, String type)    {

        ArrayList<Tourney> tourneys = new ArrayList<>();

        String  title, date, location, tourney, iconLink, titleLink, countryLink;
        String domain = "https://liquipedia.net";

        for (Element row:    rows)   {

            tourney = domain+row.child(0).child(0).child(0).attr("href");
            if (row.child(0).child(0).child(0).hasAttr("title")) {
                iconLink = domain+row.child(0).child(0).child(0).child(0).attr("src");
            }else iconLink = domain+row.child(0).child(0).child(0).attr("src");
            title = row.child(0).child(1).child(0).text();
            titleLink = domain+row.child(0).child(1).child(0).attr("href");
            date = row.child(1).text();
//            prize = row.child(2).text();
//            nop = row.child(3).child(0).text();
            location = row.child(4).child(1).text();
            countryLink = domain+row.child(4).child(0).child(0).child(0).attr("src");
//            country = domain+row.child(4).child(0).child(0).attr("href");

            tourneys.add(new Tourney(type,title,date,location,tourney,iconLink,titleLink,countryLink));
        }

        if (type.contains("Premier")) premierList = tourneys; counter++;
        if (type.contains("Major")) majorList = tourneys; counter++;
        if (type.contains("Minor")) minorList = tourneys; counter++;
        if (type.contains("Monthly")) monthlyList = tourneys; counter++;
        if (type.contains("Weekly")) weeklyList = tourneys; counter++;
        if (type.contains("Show")) showList = tourneys; counter++;
        if (type.contains("Qualifier")) qualifierList = tourneys; counter++;

        if (counter == 7) {
//            TODO refresh recyclers and broadcast splachscreen to stop
        }

//        This works perfectly
//        Collections.sort(tourneys, new Comparator<Tourney>() {
//            @Override
//            public int compare(Tourney o1, Tourney o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });

//        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
//        mRecyclerView.setLayoutManager(mGridLayoutManager);
//        TourneyAdapter tourneyAdapter = new TourneyAdapter(MainActivity.this, mTourneyList, "all");
//        mRecyclerView.setAdapter(tourneyAdapter);

    }

    class RetrieveDoc   extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... strings) {

            Document document = new Document("null");

            try {
                document = Jsoup.connect(strings[0]).get();
            }   catch (Exception    e)  {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            Elements elements    =   document.select("div.divRow");
            Element type    = document.selectFirst("#firstHeading").child(0);
            populateLists(elements, type.text());
        }

    }
}
