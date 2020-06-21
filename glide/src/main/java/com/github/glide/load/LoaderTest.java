package com.github.glide.load;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.github.glide.load.model.HttpUriLoader;
import com.github.glide.load.model.ModelLoad;
import com.github.glide.load.model.data.DataFetcher;

import java.io.InputStream;

public class LoaderTest {

    public void test(Context context) {
        Uri uri = Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592727736064&di=d2aa567719683441adcb923ac72245db&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");

        HttpUriLoader httpUriLoader = new HttpUriLoader();
        ModelLoad.LoadData<InputStream> buildData = httpUriLoader.buildData(uri);

        buildData.fetcher.loadData(new DataFetcher.DataFetcherCallback<InputStream>() {
            @Override
            public void onFetcherReady(InputStream inputStream) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Log.e("tag", "" + bitmap.getConfig().name());
            }

            @Override
            public void onLoadFailed(Exception e) {

            }
        });
    }
}
