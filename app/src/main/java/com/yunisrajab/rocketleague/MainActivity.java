package com.yunisrajab.rocketleague;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String  TAG = "RL";
    List<Tourney> tourneys;
    List<Tile>  tiles;
    RecyclerView mRecyclerView;
    Button  bSwitch;
    boolean ifTile  =   false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setBackgroundColor(Color.parseColor("#000000"));

        mRecyclerView = findViewById(R.id.recyclerview);

        tourneys = new ArrayList<>();
        tiles = new ArrayList<>();

        bSwitch = findViewById(R.id.bswitch);
        bSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifTile)   {
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                    mRecyclerView.setLayoutManager(mGridLayoutManager);
                    TourneyAdapter tourneyAdapter = new TourneyAdapter(MainActivity.this, tourneys);
                    mRecyclerView.setAdapter(tourneyAdapter);
                    ifTile = false;
                }   else {
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
                    mRecyclerView.setLayoutManager(mGridLayoutManager);
                    TileAdapter tileAdapter = new TileAdapter(MainActivity.this, tiles);
                    mRecyclerView.setAdapter(tileAdapter);
                    ifTile = true;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        new RetrieveDoc().execute("https://www.rocketleague.com/ajax/articles-infinite/?p=");
        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Portal:Tournaments");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ifTile)   {
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            TourneyAdapter tourneyAdapter = new TourneyAdapter(MainActivity.this, tourneys);
            mRecyclerView.setAdapter(tourneyAdapter);
            ifTile = false;
        }   else {
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            TileAdapter tileAdapter = new TileAdapter(MainActivity.this, tiles);
            mRecyclerView.setAdapter(tileAdapter);
            ifTile = true;
        }
    }

    private void populateLists(Elements rows)    {


        String  type="", title="", date="", prize="", nop="", location="", tourney="",
                country="", iconLink="", titleLink="", countryLink="";
        String domain = "https://liquipedia.net";

        for (Element    row:    rows)   {

            type = row.child(0).child(0).text();
            tourney = domain+row.child(0).child(1).child(0).attr("href");
//            Log.e(TAG,tourney);
            iconLink = domain+row.child(0).child(1).child(0).child(0).attr("src");
            title = row.child(0).child(2).child(0).text();
            titleLink = domain+row.child(0).child(2).child(0).attr("href");
            date = row.child(1).text();
            prize = row.child(2).text();
            nop = row.child(3).child(0).text();
            location = row.child(4).child(1).text();
            countryLink = domain+row.child(4).child(0).child(0).child(0).attr("src");
            country = domain+row.child(4).child(0).child(0).attr("href");

            tourneys.add(new Tourney(type,title,date,prize,nop,location,tourney,country,
                    iconLink,titleLink,countryLink));
            Log.e(TAG+"t",  date);
        }

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        TourneyAdapter tourneyAdapter = new TourneyAdapter(MainActivity.this, tourneys);
        mRecyclerView.setAdapter(tourneyAdapter);
    }

    private void populateNews(Elements elements)   {

        String  link, thumbSrc, title, subtitle, date;
        String  domain  =   "https://www.rocketleague.com";

        for (Element    element:    elements)    {
            link    =   domain+element.child(0).attr("href");
            thumbSrc    =   element.getElementsByClass("tile-inner").first().child(0).attr("src");
            title    =   element.getElementsByClass("article-title").first().child(0).text();
            subtitle    =   element.getElementsByClass("subtitle").first().text();
            date    =   element.getElementsByClass("date").first().text();

            tiles.add(new Tile(link,thumbSrc,title,subtitle,date));

            try {
//                date = date.lastIndexOf(" ")+"/"+date.substring(0,date.indexOf(" "))+"/"
//                        +date.substring(date.indexOf(" ")+1, date.indexOf(","));
                Date date1=new SimpleDateFormat("MMM dd, yyyy").parse(date);
                Log.e(TAG,  ""+date1);
            } catch(Exception e)    {
                Log.e(TAG, "Exception "+e);
            }
        }

//        TileAdapter tileAdapter = new TileAdapter(MainActivity.this, tiles);
//        mRecyclerView.setAdapter(tileAdapter);
    }


    class RetrieveDoc   extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... strings) {

            Document document = new Document("null");

            try {
                document = Jsoup.connect(strings[0]).get();
                Log.e(TAG,"");
            }   catch (Exception    e)  {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);

            if (document.title().contains("Liquipedia")) {
                Elements    elements    =   document.select("div.divrow");
                populateLists(elements);
            }   else {
                Elements    elements    =   document.select("div.tile");
                populateNews(elements);
            }
        }

    }
}
