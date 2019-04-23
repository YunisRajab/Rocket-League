package com.yunisrajab.rocketleague.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.felipecsl.gifimageview.library.GifImageView;
import com.yunisrajab.rocketleague.Adapters.TileAdapter;
import com.yunisrajab.rocketleague.Adapters.TourneyAdapter;
import com.yunisrajab.rocketleague.Fragments.NewsFragment;
import com.yunisrajab.rocketleague.Fragments.TourneyFragment;
import com.yunisrajab.rocketleague.Objects.Tile;
import com.yunisrajab.rocketleague.Objects.Tourney;
import com.yunisrajab.rocketleague.R;

import org.apache.commons.io.IOUtils;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static String  TAG = "RL";
    List<Tourney> mTourneyList;
    List<Tile>  mTileList;
    Toolbar mToolbar;
    BottomNavigationView    mBottomNavigationView;
    boolean ifTile  =   false;
    NewsFragment mNewsFragment;
    TourneyFragment mTourneyFragment;
    GifImageView gifView;
    int pageno = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));

        mTourneyList = new ArrayList<>();
        mTileList = new ArrayList<>();

        mTourneyFragment = new TourneyFragment();
        mNewsFragment = new NewsFragment();

        new RetrieveDoc().execute("https://www.rocketleague.com/ajax/articles-infinite/?p="+pageno);
        new RetrieveDoc().execute("https://liquipedia.net/rocketleague/Portal:Tournaments");

        mBottomNavigationView    =   findViewById(R.id.bottomNavigation);
        mBottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        Menu menu    =   mBottomNavigationView.getMenu();
        MenuItem    menuItem    =   menu.getItem(0);
        menuItem.setChecked(true);
        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomListener);

        gifView = findViewById(R.id.gifView);

        try {
            InputStream inputStream = getAssets().open("splash.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifView.setBytes(bytes);
            gifView.startAnimation();
            mBottomNavigationView.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            Log.e("RL", e.toString());
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

            mTourneyList.add(new Tourney(type,title,date,prize,nop,location,tourney,country,
                    iconLink,titleLink,countryLink));

//            index error bs play around with the format
//            LocalDate[] localDates = fixFormat(date);
//            String sDate = dateToString(localDates);
//            Log.e(TAG+"t",  sDate);
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


        Bundle  bundle   =   new Bundle();
        bundle.putString("type","all");
        bundle.putSerializable("arraylist",  (Serializable)  mTourneyList);
        mTourneyFragment.setArguments(bundle);
        setFragment(mTourneyFragment);

//        turn off splash screen
//        should wait for a broadcast from tourneys
        gifView.stopAnimation();
        gifView.setVisibility(View.GONE);
        mBottomNavigationView.setVisibility(View.VISIBLE);
        Log.e(TAG, "done splashscreen");
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

            mTileList.add(new Tile(link,thumbSrc,title,subtitle,date));
        }

//        TileAdapter tileAdapter = new TileAdapter(MainActivity.this, tiles);
//        mRecyclerView.setAdapter(tileAdapter);
    }

    private static LocalDate[] fixFormat(String dateStr)    {
        DateTimeFormatter formatter;
        String  fStr0, fStr1;
        LocalDate[]  fDate = {LocalDate.now(),LocalDate.now()};

        if (dateStr.substring(0,dateStr.indexOf(" ")).length() > 3)   {
            formatter   =   DateTimeFormatter.ofPattern("MMMM d, yyyy");
        }   else {
            formatter   =   DateTimeFormatter.ofPattern("MMM d, yyyy");
        }

        if (dateStr.contains("-"))  {
            fStr0  =   dateStr.substring(0, dateStr.indexOf("-")-1)
                    +dateStr.substring(dateStr.indexOf(","));

            if (dateStr.length() > 17)  {
                fStr1  =   dateStr.substring(dateStr.indexOf("-")+2);
            }   else {
                fStr1  =   dateStr.substring(0,3)+dateStr.substring(dateStr.indexOf("-")+1);
            }
        }   else {
            fStr0   =   dateStr;
            fStr1   =   dateStr;
        }

        fDate[0] = LocalDate.parse(fStr0,formatter);
        fDate[1] = LocalDate.parse(fStr1,formatter);

        return fDate;
    }

    private static String dateToString(LocalDate[] local) {
        DateTimeFormatter   formatter = DateTimeFormatter.ofPattern("LLL d, yyyy");
        String sDate = local[0].format(formatter);

        if (local[0].getMonthValue() != local[1].getMonthValue())   {

            String month = local[1].getMonth().toString().substring(0,1)
                    +local[1].getMonth().toString().substring(1,3).toLowerCase();
            formatter = DateTimeFormatter.ofPattern("LLL d - , yyyy");
            sDate = local[0].format(formatter);
            sDate = sDate.substring(0, sDate.indexOf(",")) + month + " "
                    + local[1].getDayOfMonth() + sDate.substring(sDate.indexOf(","));

        } else if (local[0].getDayOfMonth() != local[1].getDayOfMonth())   {

            formatter = DateTimeFormatter.ofPattern("LLL d - , yyyy");
            sDate = local[0].format(formatter);
            sDate = sDate.substring(0, sDate.indexOf(",")) + local[1].getDayOfMonth()
                    +sDate.substring(sDate.indexOf(","));
        }

        return sDate;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener   bottomListener   =   new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId())   {
                case R.id.bn_tourneys:
                    setFragment(mTourneyFragment);
                    mToolbar.setTitle("Tournaments");
                    break;
                case R.id.bn_news:
                    setFragment(mNewsFragment);
                    mToolbar.setTitle("News & Updates");
                    break;
                case R.id.bn_misc:
//                    setFragment(mMiscFragment);
                    mToolbar.setTitle("Misc");
                    break;
                case R.id.bn_settings:
//                    setFragment(mSettingsFragment);
                    mToolbar.setTitle("Settings");
                    break;
            }
            return true;
        }
    };

    private void setFragment(Fragment fragment)  {
        FragmentTransaction fragmentTransaction =   getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,  fragment);
        fragmentTransaction.commitAllowingStateLoss(); //read into this
//        https://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
    }

//    Keep this to prevent a bug
//    https://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState, outPersistentState);
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
