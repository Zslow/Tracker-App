package com.example.tracker.util;

import android.util.Log;

import com.example.tracker.Coordinate;
import com.example.tracker.CoordinateB;
import com.example.tracker.JsonCoordinate;
import com.google.gson.Gson;
import com.example.tracker.Item;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;

public class Mapper {

    public List map(String json){
        Type collectionType = new TypeToken<List<Item>>(){}.getType();
        List<Item> itemList = new Gson().fromJson(json, collectionType);

        return itemList;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static List<Item> readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json= readAll(rd);
            Log.d("TRACKER","JSON:  "+json);

            Type collectionType = new TypeToken<List<Item>>(){}.getType();
            List<Item> itemList = new Gson().fromJson(json, collectionType);

            return itemList;
        }
        finally {
            is.close();
        }
    }

    public static List <JsonCoordinate> readCoordinatesFromUrl(String urlLat, String urlLong) throws IOException, JSONException {
        InputStream isLat=null;
        InputStream isLong=null;
        Coordinate lat;
        CoordinateB longi;
        double dif=0;
        try {

            isLat = new URL(urlLat).openStream();
            isLong = new URL(urlLong).openStream();

            BufferedReader rdLat = new BufferedReader(new InputStreamReader(isLat, Charset.forName("UTF-8")));
            String jsonLat = readAll(rdLat);
            Log.d("TRACKER", "Lat:  " + jsonLat);
            BufferedReader rdLong = new BufferedReader(new InputStreamReader(isLong, Charset.forName("UTF-8")));
            String jsonLong = readAll(rdLong);
            Log.d("TRACKER", "Long:  " + jsonLong);

            Type TypeA = new TypeToken<Coordinate>(){}.getType();
            lat = new Gson().fromJson(jsonLat, TypeA);

            Type TypeB = new TypeToken<CoordinateB>(){}.getType();
            longi = new Gson().fromJson(jsonLong, TypeB);
            dif = (Double.parseDouble(longi.getUpdatedAt())-Double.parseDouble(lat.getUpdatedAt()));
            Log.d("TRACKER", "DIFERENCIA:  " + dif);



            ArrayList <JsonCoordinate>  list = new ArrayList<>();
            list.add(lat);
            list.add(longi);
            Log.d("TRACKER", "LISTA:  " + list.get(0).toString()+ ","+list.get(1).toString());
            return list;
        }
        finally {
            if(isLat != null)
                isLat.close();
            if(isLong!= null)
                isLong.close();
        }
    }
}
