package com.android.flickerapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.flickerapp.adapter.ImageAdapter;
import com.android.flickerapp.model.Photo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private ImageAdapter adapter;

    @BindView(R.id.pager)
    public ViewPager viewPager;

    private ArrayList<Photo> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        photoList = new ArrayList<>();

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        String photos = i.getStringExtra("photo_list");

        Gson gson = new Gson();

        List<Photo> list = gson.fromJson(photos, new TypeToken<List<Photo>>(){}.getType());

        if(list != null){

            photoList.addAll(list);

            adapter = new ImageAdapter(this, photoList);

            viewPager.setAdapter(adapter);

            // displaying selected image first
            viewPager.setCurrentItem(position);
        }

    }
}
