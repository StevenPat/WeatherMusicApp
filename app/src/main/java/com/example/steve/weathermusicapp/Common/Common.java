package com.example.steve.weathermusicapp.Common;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Steve on 2/1/2017.
 */

public class Common {
    public static String API_KEY = "4a3e95e39023825f2bca45cd2a70e3e7";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";

    @NonNull
    public static String apiRequest(String lat, String lng){
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?lat=%s&lon=%s&APPID=%s&units=imperial", lat, lng,API_KEY));
        return sb.toString();
    }
    @NonNull
    public static String apiRequestZip(String zip, String country){
            String link = API_LINK + "?zip=" + zip +","+ country+"&APPID="+ API_KEY+"&units=imperial";
        return link;
    }
    public static  String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH.mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return  dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
