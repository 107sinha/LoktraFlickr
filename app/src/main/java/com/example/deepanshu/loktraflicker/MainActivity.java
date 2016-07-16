package com.example.deepanshu.loktraflicker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.deepanshu.loktraflicker.adapter.FlickrAdapter;
import com.example.deepanshu.loktraflicker.model.Flickr;
import com.example.deepanshu.loktraflicker.model.FlickrImage;
import com.example.deepanshu.loktraflicker.rest.DownloadFlickrImageTask;
import com.example.deepanshu.loktraflicker.rest.FlickrClient;
import com.example.deepanshu.loktraflicker.rest.FlickrTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    String[] IMAGE = {
            "https://farm9.staticflickr.com/8780/27691558324_e502272ed0_m.jpg",
            "https://farm8.staticflickr.com/7309/27691559244_bf9f281086_m.jpg",
            "https://farm9.staticflickr.com/8814/27692150353_bb49631d07_m.jpg",
            "https://farm9.staticflickr.com/8652/28026215710_ee387b5b6d_m.jpg",
            "https://farm9.staticflickr.com/8729/28203837402_bf7d3d8525_m.jpg",
            "https://farm9.staticflickr.com/8780/27691558324_e502272ed0_m.jpg",
            "https://farm8.staticflickr.com/7309/27691559244_bf9f281086_m.jpg",
            "https://farm9.staticflickr.com/8814/27692150353_bb49631d07_m.jpg",
            "https://farm9.staticflickr.com/8652/28026215710_ee387b5b6d_m.jpg",
            "https://farm9.staticflickr.com/8729/28203837402_bf7d3d8525_m.jpg"
    };

    private ArrayList<FlickrImage> images;
    private FlickrAdapter mAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        images = new ArrayList<>();
        mAdapter = new FlickrAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        
//        myClickHandler();
        flickImages();
    }

    private void flickImages() {

        // Create an instance of our Flickr API interface.
        FlickrTask service = FlickrClient.getClient().create(FlickrTask.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<Flickr> call = service.getPhotos();

        call.enqueue(new Callback<Flickr>() {
            @Override
            public void onResponse(Call<Flickr> call, Response<Flickr> response) {
                Log.d(TAG, "" + response.body().getTitle());
                // task successfull
                images.clear();

                for (int i = 0; i < response.body().getItems().size(); i++) {
                    FlickrImage flickrImage = new FlickrImage();

                    flickrImage.setImage(response.body().getItems().get(i).getMedia().getM());
                    flickrImage.setTitle(response.body().getItems().get(i).getTitle());
                    flickrImage.setAuthor(response.body().getItems().get(i).getAuthor());
                    flickrImage.setDate(response.body().getItems().get(i).getDateTaken());
                    flickrImage.setPublished(response.body().getItems().get(i).getPublished());

                    images.add(flickrImage);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Flickr> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                // stopping swipe refresh
            }
        });
    }

    public void myClickHandler() {
        // Check the Network Connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            new DownloadFlickrImageTask().execute("https://api.flickr.com/services/feeds/photos_public.gne?format=json");
        } else {
            // display error
            Toast.makeText(getApplicationContext(), "No Network Connection Available.", Toast.LENGTH_LONG).show();
        }
    }

}
