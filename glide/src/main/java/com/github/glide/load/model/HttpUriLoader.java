package com.github.glide.load.model;

import android.net.Uri;
import android.webkit.URLUtil;

import com.github.glide.ObjectKey;
import com.github.glide.load.model.data.HttpUrlFetcher;

import java.io.InputStream;

public class HttpUriLoader implements ModelLoad<Uri, InputStream> {
    @Override
    public boolean handle(Uri uri) {
        return URLUtil.isNetworkUrl(uri.getScheme());
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<InputStream>(new ObjectKey(uri), new HttpUrlFetcher(uri));
    }
}
