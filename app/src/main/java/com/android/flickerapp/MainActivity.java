package com.android.flickerapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.flickerapp.dao.DatabaseHelper;
import com.android.flickerapp.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private SessionManager sessionManager ;
    private DatabaseHelper databaseHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        loadData();
    }

    @OnClick(R.id.btn_flickr)
    public void onFlickrButtonClick(View view){

        Intent intent = new Intent(MainActivity.this,FlickrActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_database)
    public void onDatabaseButtonClick(View view){

        Intent intent = new Intent(MainActivity.this,DatabaseActivity.class);
        startActivity(intent);
    }

    private void loadData(){

        if(sessionManager.isFirstTime()){

            databaseHelper.insertContact("48f3", "1196","Gmail");
            databaseHelper.insertContact("3e47", "f1fe","Gmail");
            databaseHelper.insertContact("2cac", "036e","Gmail1");

            databaseHelper.insertAccount(1,"test_one@gmail.com","Gmail");
            databaseHelper.insertAccount(0,"test_two@gmail.com","Gmail1");

            sessionManager.setFirstTime(false);
        }
    }
}
