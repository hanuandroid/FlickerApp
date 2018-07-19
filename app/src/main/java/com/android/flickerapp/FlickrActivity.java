package com.android.flickerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.flickerapp.adapter.GalleryAdapter;
import com.android.flickerapp.app.AppController;
import com.android.flickerapp.model.FlickrResponse;
import com.android.flickerapp.model.Photo;
import com.android.flickerapp.service.ApiClient;
import com.android.flickerapp.utils.NetworkStatus;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;





public class FlickrActivity extends AppCompatActivity implements GalleryAdapter.Selection,
        AdapterView.OnItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tags_spinner)
    public Spinner tagsSpinner;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private ArrayList<Photo> photoList;

    private NetworkStatus networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr);
        ButterKnife.bind(this);

        networkStatus = new NetworkStatus(this);
        photoList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(this, photoList);
        galleryAdapter.setListener(this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(galleryAdapter);

        loadTagsView();

        //loadFlickrImage("sunlight");
    }

    public void loadTagsView() {

        // Spinner click listener
        tagsSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> tagsList = new ArrayList<>();
        tagsList.add("sunlight");
        tagsList.add("fire");
        tagsList.add("coth5");
        tagsList.add("bright");
        tagsList.add("national");
        tagsList.add("milkyway");
        tagsList.add("beach");
        tagsList.add("water");
        tagsList.add("sky");
        tagsList.add("red");
        tagsList.add("flower");
        tagsList.add("nature");
        tagsList.add("blue");
        tagsList.add("night");
        tagsList.add("white");

        tagsList.add("tree");
        tagsList.add("green");
        tagsList.add("flowers");
        tagsList.add("portrait");
        tagsList.add("art");
        tagsList.add("light");
        tagsList.add("snow");

        tagsList.add("dog");
        tagsList.add("sun");
        tagsList.add("clouds");
        tagsList.add("cat");
        tagsList.add("park");
        tagsList.add("winter");
        tagsList.add("city");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tagsList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tagsSpinner.setAdapter(dataAdapter);
    }

    public void loadFlickrImage(String tags) {

        if (networkStatus.isConnectingToInternet()) {

            loadList(ApiClient.BASE_URL + tags);
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_SHORT).show();
        }
    }


/*    public void loadFlickrImage(String tags) {

        if (networkStatus.isConnectingToInternet()) {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<FlickrResponse> call = apiService.getFlickrPhotoList(tags);
            call.enqueue(new Callback<FlickrResponse>() {
                @Override
                public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {

                    FlickrResponse flickrResponse = response.body();

                    if (flickrResponse != null) {
                        if (flickrResponse.getPhotos() != null) {

                            List<Photo> list = flickrResponse.getPhotos().getPhoto();

                            if (list != null) {
                                photoList.addAll(list);
                                galleryAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<FlickrResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });

        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void loadList(String url) {

        String tag_json_obj = "json_obj_req";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        pDialog.hide();

                        if (response != null) {

                            String result = response.toString();

                            Gson gson = new Gson();

                            FlickrResponse flickrResponse = gson.fromJson(result, FlickrResponse.class);

                            if (flickrResponse != null) {
                                if (flickrResponse.getPhotos() != null) {

                                    List<Photo> list = flickrResponse.getPhotos().getPhoto();

                                    if (list != null) {

                                        if (photoList.size() > 0) {
                                            photoList.clear();
                                        }

                                        photoList.addAll(list);
                                        galleryAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    @Override
    public void onGridItemClick(int position) {

        Gson gson = new Gson();
        String photos = gson.toJson(photoList);

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("position", position);
        i.putExtra("photo_list", photos);
        startActivity(i);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String item = adapterView.getItemAtPosition(position).toString();

        loadFlickrImage(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
