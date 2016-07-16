package com.example.deepanshu.loktraflicker.rest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by deepanshu on 15/7/16.
 */
public class DownloadFlickrImageTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(strings[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String s) {
        Log.d("TAG", "hell" + s);
    }

    // Connect and Download Data
    private String downloadUrl(String string) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(string);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("TAG", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readIt(InputStream is) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(is, "UTF-8");
        char[] buffer = new char[10000];
        reader.read(buffer);
        return new String(buffer);
    }

}
