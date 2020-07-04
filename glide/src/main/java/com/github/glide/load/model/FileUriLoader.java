package com.github.glide.load.model;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.URLUtil;

import com.github.glide.ObjectKey;
import com.github.glide.load.model.data.FileUriFetcher;

import java.io.InputStream;

public class FileUriLoader implements ModelLoad<Uri, InputStream> {
    private ContentResolver cr;

    public FileUriLoader(ContentResolver cr) {
        this.cr = cr;
    }


    @Override
    public boolean handle(Uri uri) {
        return URLUtil.isFileUrl(uri.toString());
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<>(new ObjectKey(uri), new FileUriFetcher(uri, cr));
    }
}
