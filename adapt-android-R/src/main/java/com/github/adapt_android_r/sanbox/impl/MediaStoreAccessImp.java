package com.github.adapt_android_r.sanbox.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;


import androidx.annotation.RequiresApi;

import com.github.adapt_android_r.sanbox.BaseRequest;
import com.github.adapt_android_r.sanbox.FileResponse;
import com.github.adapt_android_r.sanbox.IFile;
import com.github.adapt_android_r.sanbox.annotion.DbField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

//Android 11   实现
@RequiresApi(api = Build.VERSION_CODES.Q)
public class MediaStoreAccessImp implements IFile {

    //    uri
    public static final String AUDIO = "Audio";
    public static final String VIDEO = "Video";
    public static final String IMAGE = "Pictures";
    public static final String DOWNLOADS = "Downloads";//什么都可以放
    //    外置卡的uri 放到map
    HashMap<String, Uri> uriMap = new HashMap<>();
    private static MediaStoreAccessImp sInstance;

    public static MediaStoreAccessImp getInstance() {
        if (sInstance == null) {
            synchronized (MediaStoreAccessImp.class) {
                if (sInstance == null) {
                    sInstance = new MediaStoreAccessImp();
                }
            }
        }
        return sInstance;
    }

    private MediaStoreAccessImp() {
        uriMap.put(AUDIO, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        uriMap.put(VIDEO, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        uriMap.put(IMAGE, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        uriMap.put(DOWNLOADS, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
    }

    @Override
    public <T extends BaseRequest> FileResponse newCreateFile(Context context, T baseRequest) {
        Uri uri = uriMap.get(baseRequest.getType());
        ContentValues contentValues = objectConvertValues(baseRequest);
        ContentResolver contentResolver = context.getContentResolver();
        Uri resultUri = contentResolver.insert(uri, contentValues);
        FileResponse fileResponce = new FileResponse(resultUri, contentResolver);
        if (resultUri != null) {
            fileResponce.setSuccess(true);
            return fileResponce;
        }
        fileResponce.setSuccess(false);
        return fileResponce;
    }

    private <T extends BaseRequest> ContentValues objectConvertValues(T baseRequest) {

        ContentValues contentValues = new ContentValues();
        Field[] fields = baseRequest.getClass().getDeclaredFields();


        for (Field field : fields) {
            DbField dbField = field.getAnnotation(DbField.class);
            if (dbField == null) {
                continue;
            }
            String key = dbField.value();
            String value = null;
            try {
//                field.get(baseRequest);//1       //2  getPath  Method   path
                String fieldName = field.getName();//path
                char firstLetter = Character.toUpperCase(fieldName.charAt(0));//P
                String theRest = fieldName.substring(1);//ath
                String methodName = "get" + firstLetter + theRest;//getPath
                Method getMethod = baseRequest.getClass().getMethod(methodName);
                value = (String) getMethod.invoke(baseRequest);
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    contentValues.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contentValues;
    }

    @Override
    public <T extends BaseRequest> FileResponse delete(Context context, T baseRequest) {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse renameTo(Context context, T where, T request) {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse copyFile(Context context, T baseRequest) {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse query(Context context, T baseRequest) {
        return null;
    }
}
