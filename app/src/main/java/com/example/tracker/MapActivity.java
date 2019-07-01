package com.example.tracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GetCoordinates task = null;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_demo);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        updateMap();


    }

    public void updateMap(){
        DownloadCallback dc = new DownloadCallback(){
            public void onSuccess(List list){
                if(list != null) {
                    Log.d("tracker", "Downloaded the string: " + list.toString());
                    List<JsonCoordinate> listC =(List <JsonCoordinate>) list;
                    try {
                        double dif = Double.parseDouble(listC.get(0).getUpdatedAt())- Double.parseDouble(listC.get(1).getUpdatedAt());
                        if(dif > 120000 || dif < -120000){
                            updateMap();
                        }
                        else
                            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(listC.get(0).getLat()), Double.parseDouble(listC.get(1).getLat()))).title("Marker"));
                    } catch (NullPointerException npe){
                        Log.d("TRACKER", "NULL POINTER EX: "+ npe.getMessage());
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                    String msg = getResources().getString(R.string.message_Error);

                    builder.setMessage(msg)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    finish();
                                }
                            });
                    AlertDialog popup = builder.create();

                    popup.show();
                }
            }
            public void onFailure(Exception e){
                Log.d("tracker", "Download had a serious failure: "+ e.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                String msg = getResources().getString(R.string.message_Error);

                builder.setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();
                            }
                        });
                AlertDialog popup = builder.create();

                popup.show();

            }
        };

        task = new GetCoordinates(dc);
        task.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(task != null && task.getStatus() == GetUsers.Status.RUNNING){

            task.cancel(true);
            task = null;
        }

    }


}
