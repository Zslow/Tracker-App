package com.example.tracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GetUsers task=null;

    private  ItemList itemList = ItemList.getItemList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoIntent;
                nuevoIntent = new Intent(MainActivity.this,NewUserActivity.class);
                nuevoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nuevoIntent);
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });

        recyclerView =  findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(task != null && task.getStatus() == GetUsers.Status.RUNNING){

            task.cancel(true);
            task = null;
        }

    }

    public void updateList(){

        DownloadCallback dc = new DownloadCallback(){
            public void onSuccess(List list){
                if(list != null) {
                    Log.d("tracker", "Downloaded the string: " + list.toString());
                    itemList.setLista(list);
                    mAdapter = new RecyclerAdapter(itemList.getLista());
                    recyclerView.setAdapter(mAdapter);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

        task = new GetUsers(dc);
        task.execute();

    }

    protected void onStart() {
        super.onStart();
        //Log.d("paygo"," on start");
        Bundle extras = getIntent().getExtras();
        if(itemList.getLista()==null){
            updateList();
            return;
        }

        if (extras != null) {

            String name =extras.getString("name");
            String job =extras.getString("job");
            String age =extras.getString("age");
            String gender =extras.getString("gender");
            Item item = new Item(name, gender,age,job);
            List <Item> lis =itemList.getLista();
            lis.add(item);
            itemList.setLista(lis);
            mAdapter = new RecyclerAdapter(lis);
            recyclerView.setAdapter(mAdapter);
            getIntent().removeExtra("name");
            getIntent().removeExtra("job");
            getIntent().removeExtra("age");
            getIntent().removeExtra("gender");

        }
        else{
            mAdapter = new RecyclerAdapter(itemList.getLista());
            recyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
