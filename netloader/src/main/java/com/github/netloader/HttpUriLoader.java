package com.github.netloader;

import android.net.Uri;
import android.webkit.URLUtil;


import com.github.netloader.data.HttpUrlFetcher;

import java.io.InputStream;

public class HttpUriLoader implements ModelLoad<Uri, InputStream> {
    @Override
    public boolean handle(Uri uri) {
        return URLUtil.isNetworkUrl(uri.getScheme());
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<InputStream>(new HttpUrlFetcher(uri));
    }
}
