package com.github.glide.load.model;

import android.content.res.AssetManager;
import android.net.Uri;
import android.webkit.URLUtil;

import com.github.glide.ObjectKey;
import com.github.glide.load.model.data.AssetUriFetcher;

import java.io.InputStream;

public class AssetsUruLoader implements ModelLoad<Uri, InputStream> {
    private AssetManager assetManager;

    public AssetsUruLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public boolean handle(Uri uri) {
        return URLUtil.isAssetUrl(uri.toString());
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<>(new ObjectKey(uri), new AssetUriFetcher(assetManager, uri));
    }
}
