package com.yunisrajab.rocketleague.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class Tourney {

    private String  type, title, date, location, tourney, titleLink;
    private Bitmap iconLink, countryLink;

    public Tourney(String type, String title, String date, String location, String tourney,
                   String iconLink, String titleLink, String countryLink) {
        this.type   =   type;
        this.title   =   title;
        this.date   =   date;
        this.location   =   location;

        Bitmap dummy = null;
//        try {
//            URL url1 = new URL(iconLink);
//            URL url2 = new URL(countryLink);
//            this.iconLink = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
//            this.countryLink = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
//        } catch(IOException e) {
//            System.out.println(e);
//        }

        new getBitmap().execute(new ImageObject(iconLink, "icon", dummy));
        new getBitmap().execute(new ImageObject(countryLink, "country", dummy));

        this.tourney   =   tourney;
        this.titleLink   =   titleLink;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
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

    public String getTourney() {
        return tourney;
    }

    class ImageObject {
        String link, type;
        Bitmap bitmap;
        ImageObject(String link, String type, Bitmap bitmap) {
            this.link = link;
            this.type = type;
            this.bitmap = bitmap;
        }
    }

    class   getBitmap   extends AsyncTask<ImageObject,Void,ImageObject>   {
        @Override
        protected ImageObject doInBackground(ImageObject... imageObject) {
            try {
                URL url = new URL(imageObject[0].link);
                imageObject[0].bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }
            return imageObject[0];
        }

        @Override
        protected void onPostExecute(ImageObject imageObject) {
            super.onPostExecute(imageObject);
            if (imageObject.type.contains("icon")) iconLink = imageObject.bitmap;
            else countryLink = imageObject.bitmap;
        }
    }
}
