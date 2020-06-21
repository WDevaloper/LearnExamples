package com.github.glide.load.model.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlFetcher implements DataFetcher<InputStream> {
    private Uri uri;
    private boolean isCancel = false;

    public HttpUrlFetcher(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void loadData(DataFetcherCallback<InputStream> callback) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            int responseCode = urlConnection.getResponseCode();
            if (isCancel) {
                return;
            }


            if (responseCode == HttpURLConnection.HTTP_OK) {
                callback.onFetcherReady(inputStream);
            } else {
                callback.onLoadFailed(new RuntimeException(urlConnection.getResponseMessage()));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    public void cancel() {
        isCancel = true;
    }
}
