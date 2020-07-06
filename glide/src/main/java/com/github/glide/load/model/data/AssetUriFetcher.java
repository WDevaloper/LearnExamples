package com.github.glide.load.model.data;

import android.content.res.AssetManager;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

public class AssetUriFetcher implements DataFetcher<InputStream> {
    private AssetManager assetManager;
    private Uri uri;

    public AssetUriFetcher(AssetManager assetManager, Uri uri) {
        this.assetManager = assetManager;
        this.uri = uri;
    }

    @Override
    public void loadData(DataFetcherCallback<InputStream> callback) {
        InputStream is = null;
        try {
            is = assetManager.open(uri.toString());
            callback.onFetcherReady(is);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onLoadFailed(e);
        } finally {
            if (is != null) {
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

    }
}
