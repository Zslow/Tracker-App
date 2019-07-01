package com.example.tracker;

import com.example.tracker.Item;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.example.tracker.DownloadCallback;
import com.example.tracker.util.Mapper;

public class GetUsers extends AsyncTask<String, Void, List> {

    private final String url = "https://visiotech-developer-test.s3-eu-west-1.amazonaws.com/people.json";
    DownloadCallback dc = null;

    public GetUsers(DownloadCallback dc){
        this.dc = dc;
    }

    @Override
    protected List<Item> doInBackground(String... params) {


        List<Item> itemList ;




        try {

            itemList = Mapper.readJsonFromUrl(url);


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
