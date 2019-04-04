package com.yunisrajab.rocketleague.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class Tourney {

    private String  type, title, date, prize, nop, location, tourney, titleLink, country;
    private Bitmap iconLink, countryLink;

    public Tourney(String type, String title, String date, String prize, String nop, String location,
                   String tourney, String country, String iconLink, String titleLink, String countryLink) {
        this.type   =   type;
        this.title   =   title;
        this.date   =   date;
        this.prize   =   prize;
        this.nop   =   nop;
        this.location   =   location;

//        try {
//            URL url1 = new URL(iconLink);
//            URL url2 = new URL(countryLink);
//            this.iconLink = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
//            this.countryLink = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
//        } catch(IOException e) {
//            System.out.println(e);
//        }

        new getBitmap().execute(iconLink);

        this.tourney   =   tourney;
        this.titleLink   =   titleLink;
        this.country   =   country;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getNop() {
        return nop;
    }

    public String getPrize() {
        return prize;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getTitleLink() {
        return titleLink;
    }

    public Bitmap getCountryLink() {
        return countryLink;
    }

    public Bitmap getIconLink() {
        return iconLink;
    }

    public String getCountry() {
        return country;
    }

    public String getTourney() {
        return tourney;
    }


    class   getBitmap   extends AsyncTask<String,Void,Bitmap>   {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap  bitmap = null;
            try {
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iconLink = bitmap;
            countryLink = bitmap;
//            TODO add countrylink bitmap
        }
    }
}
