package com.github.glide.load.model.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUriFetcher implements DataFetcher<InputStream> {
    private Uri uri;
    private ContentResolver cr;
    private boolean isCancel = false;

    public FileUriFetcher(Uri uri, ContentResolver resolver) {
        this.uri = uri;
        this.cr = resolver;
    }

    @Override
    public void loadData(DataFetcherCallback<InputStream> callback) {
        InputStream is = null;

        try {
            is = cr.openInputStream(uri);
            callback.onFetcherReady(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.onLoadFailed(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void cancel() {
        isCancel = true;
    }
}
