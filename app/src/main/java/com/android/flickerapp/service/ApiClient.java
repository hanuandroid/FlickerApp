package com.android.flickerapp.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://api.flickr.com/services/rest/?format=json" +
            "&nojsoncallback=1&api_key=04a42d236e746206fbbf64245342dd2d" +
            "&method=flickr.photos.search&per_page=30&page=1&tags=";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
