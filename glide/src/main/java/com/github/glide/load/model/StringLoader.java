package com.github.glide.load.model;

import android.net.Uri;

import java.io.InputStream;

public class StringLoader implements ModelLoad<String, InputStream> {
    private final ModelLoad<Uri, InputStream> modelLoad;

    public StringLoader(ModelLoad<Uri, InputStream> modelLoad) {
        this.modelLoad = modelLoad;
    }

    @Override
    public boolean handle(Uri uri) {
        return false;
    }

    @Override
    public LoadData<InputStream> buildData(String model) {
        return modelLoad.buildData(Uri.parse(model));
    }
}
