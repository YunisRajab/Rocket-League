package com.yunisrajab.rocketleague.Objects;

import android.graphics.Bitmap;

public class Tourney {

    private String  type, title, date, location, tourney, titleLink, iconLink, countryLink;;
    private Bitmap icon, country;

    public Tourney(String type, String title, String date, String location, String tourney,
                   String iconLink, String titleLink, String countryLink) {
        this.type   =   type;
        this.title   =   title;
        this.date   =   date;
        this.location   =   location;
        this.iconLink   =   iconLink;
        this.countryLink   =   countryLink;
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

    public String getTourney() {
        return tourney;
    }

    public Bitmap getCountry() {
        return country;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getCountryLink() {
        return countryLink;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setCountry(Bitmap country) {
        this.country = country;
    }
}
