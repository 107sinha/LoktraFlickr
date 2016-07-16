package com.example.deepanshu.loktraflicker.rest;

import com.example.deepanshu.loktraflicker.model.Flickr;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by deepanshu on 14/7/16.
 */
public interface FlickrTask {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/services/feeds/photos_public.gne?format=json")
    Call<Flickr> getPhotos();
}
