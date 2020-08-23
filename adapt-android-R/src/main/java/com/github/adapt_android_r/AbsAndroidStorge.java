package com.github.adapt_android_r;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

public abstract class AbsAndroidStorge implements AndroidStorge {
    private Context context;

    public AbsAndroidStorge(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public Uri insert(String imageName, String imageType, String relativePath) {
        Uri insertUri;
        ContentResolver resolver = context.getContentResolver();
        //设置文件参数到ContentValues中
        ContentValues values = new ContentValues();
        //设置文件名
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        //设置文件描述，这里以文件名代替
        values.put(MediaStore.Images.Media.DESCRIPTION, imageName);
        //设置文件类型为image/*
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + imageType);
        //注意：MediaStore.Images.Media.RELATIVE_PATH需要targetSdkVersion=29,
        //故该方法只可在Android10的手机上执行
        values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
        //EXTERNAL_CONTENT_URI代表外部存储器
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        //insertUri表示文件保存的uri路径
        insertUri = resolver.insert(external, values);
        return insertUri;
    }
}
