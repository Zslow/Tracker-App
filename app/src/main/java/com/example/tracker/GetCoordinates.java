package com.example.tracker;


import android.os.AsyncTask;
import android.util.Log;

import com.example.tracker.util.Mapper;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.List;

public class GetCoordinates  extends AsyncTask<String, Void, List> {

    private final String urlLat = "https://69laxwwkx5.execute-api.eu-west-2.amazonaws.com/default/getLatitude";
    private final String urlLong = " https://3si2ani0ta.execute-api.eu-west-2.amazonaws.com/default/getLongitude";
    DownloadCallback dc = null;

public GetCoordinates(DownloadCallback dc){
        this.dc = dc;
        }

@Override
protected List<JsonCoordinate> doInBackground(String... params) {


        List<JsonCoordinate> itemList ;




        try {

        itemList = Mapper.readCoordinatesFromUrl(urlLat,urlLong);


        } catch (SocketTimeoutException t) {
        t.printStackTrace();
        itemList = null;
        } catch (InterruptedIOException ioi) {
        Log.d("tracker","Get users task cancelada ");
        itemList = null;
        }
        catch (IOException i) {
        i.printStackTrace();
        itemList = null;
        } catch (Exception q) {
        q.printStackTrace();
        itemList = null;
        }

        return itemList;

        }

@Override
protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected void onPostExecute(List itemList) {
        if(itemList != null)
        dc.onSuccess(itemList);

        else
        Log.d("tracker" , " result = NULL");
        }


        }
