package com.android.flickerapp.service;

import com.android.flickerapp.model.FlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {


    @GET("{tags}")
    Call<FlickrResponse> getFlickrPhotoList(@Path("tags") String tags);
}
