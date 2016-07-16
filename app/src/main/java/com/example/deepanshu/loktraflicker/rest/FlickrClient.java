package com.example.deepanshu.loktraflicker.rest;

import retrofit2.Retrofit;

/**
 * Created by deepanshu on 14/7/16.
 */
public class FlickrClient {

    public static final String BASE_URL = "https://api.flickr.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JsonpGsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
