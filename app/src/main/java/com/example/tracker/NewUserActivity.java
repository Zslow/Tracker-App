package com.example.tracker;

import android.app.Dialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.tracker.util.KeyBoardHider;


public class NewUserActivity extends AppCompatActivity{


    private EditText name;
    private EditText job;
    private EditText age;
    private RadioGroup gender;

    private String names ;
    private String jobs ;
    private String ages ;
    private String genders ;
    private FloatingActionButton fab;

    private Dialog dialog;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        fab = findViewById(R.id.fab);

        name = (EditText) findViewById(R.id.etName);
        job = (EditText) findViewById(R.id.etJob);
        age= (EditText) findViewById(R.id.etAge);
        gender =  findViewById(R.id.RGroup);
        addListenerOnButtons();

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



    }

    @Override
    protected void onPause(){
        super.onPause();

        hideKeyboard();

        if (dialog != null){
            if (dialog.isShowing()) {

                dialog.dismiss();
            }
        }

    }

    public void addListenerOnButtons() {




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = checkInput();
                if(msg.equals("ok")){

                    executeTask();

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewUserActivity.this);
                    builder.setMessage(msg)

                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog popup = builder.create();
                    popup.show();
                }
            }
        });

    }

    public void executeTask(){
        Intent results = new Intent(NewUserActivity.this,MainActivity.class);
        results.putExtra("name", names);
        results.putExtra("job", jobs);
        results.putExtra("age", ages);
        results.putExtra("gender", genders);

        results.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(results);
        finish();
    }

    public String checkInput(){
        names = name.getText().toString();
        jobs = job.getText().toString();
        ages = age.getText().toString();
        int checkedId = gender.getCheckedRadioButtonId();
        if (checkedId == R.id.RMale){
            genders = "Male";
        }else if (checkedId == R.id.RFemale){
            genders = "Female";
        }else if (checkedId == R.id.ROther){
            genders = "Other";
        }

        if(names.equals("")|| jobs.equals("")|| ages.equals("")|| genders.equals(""))
            return getResources().getString(R.string.messageBadInput);
        /*
        //letras o números seguido opcionalmente de ". - _" y mas letras o números
        if( !userNames.matches("^\\w+(\\w+)?"))
            return getResources().getString(R.string.messageBadUser);
        if( !pwds.matches("^\\w{8}"))
            return getResources().getString(R.string.messageBadPassword);*/
        /*if( !dnis.matches("^\\d{7,8}[a-zA-Z]"))
            return getResources().getString(R.string.messageBadDni);
        if( !ibans.matches("^[a-zA-Z]{2}\\d{22}") && !ibans.matches("^\\d{22}") )
            return getResources().getString(R.string.messageBadIban);*/

        return "ok";
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    public void hideKeyboard(){
        KeyBoardHider.hideKeyboard(this);
    }

}
