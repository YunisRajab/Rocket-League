package com.yunisrajab.rocketleague.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class Tile {

    private String  link, title, subtitle, date;
    Bitmap  img;

    public Tile(String link, String thumbSrc, String title, String subtitle, String date)   {
        this.title = title;
        this.subtitle = subtitle;
        this.link = link;
        this.date = date;

        new getBitmap().execute(thumbSrc);
    }

    public String getLink() {
        return link;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getImg() {
        return img;
    }

    class   getBitmap   extends AsyncTask<String,Void,Bitmap> {
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
            img = bitmap;
        }
    }
}
