package com.github.adapt_android_r.sanbox.file.impl;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.request.impl.CopyRequest;
import com.github.adapt_android_r.sanbox.request.impl.FileRequest;
import com.github.adapt_android_r.sanbox.response.FileResponse;
import com.github.adapt_android_r.sanbox.file.IFile;
import com.github.adapt_android_r.sanbox.uitls.UriTypeUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Android 11   实现
@RequiresApi(api = Build.VERSION_CODES.Q)
public class MediaStoreAccessImp implements IFile {
    //    uri
    public static final String AUDIO = "Audio";
    public static final String VIDEO = "Video";
    public static final String IMAGE = "Pictures";
    public static final String DOWNLOADS = "Downloads";//什么都可以放
    //    外置卡的uri 放到map
    private HashMap<String, Uri> uriMap = new HashMap<>();
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
        Uri uri = uriMap.get(baseRequest.getUriType());
        FileResponse fileResponse = new FileResponse(context.getContentResolver());
        ContentValues contentValues = baseRequest.getContentValues();
        if (uri == null) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }

        Uri resultUri = context.getContentResolver().insert(uri, contentValues);
        if (resultUri == null) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }
        fileResponse.setSuccess(true);
        fileResponse.setUri(resultUri);
        return fileResponse;
    }

    //删除
    @Override
    public <T extends BaseRequest> FileResponse delete(Context context, T baseRequest) {
        Uri uri = query(context, baseRequest).getUri();
        FileResponse fileResponse = new FileResponse(context.getContentResolver());
        if (uri == null) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }
        int delete = context.getContentResolver().delete(uri, null, null);
        if (delete <= 0) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }
        fileResponse.setSuccess(true);
        return fileResponse;
    }

    //重命名文件
    @Override
    public <T extends BaseRequest> FileResponse renameTo(Context context, T where, T request) {
        Uri uri = query(context, where).getUri();
        FileResponse fileResponse = new FileResponse(context.getContentResolver());
        if (uri == null) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }
        ContentValues contentValues = request.getContentValues();
        int code = context.getContentResolver().update(uri, contentValues, null, null);
        if (code <= 0) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }
        fileResponse.setSuccess(true);
        fileResponse.setUri(uri);
        return fileResponse;

    }

    @Override
    public <T extends BaseRequest> FileResponse copyFile(Context context, T baseRequest) {
        FileResponse fileResponse = new FileResponse(context.getContentResolver());
        CopyRequest copyRequest = (CopyRequest) baseRequest;

        // 判断源文件存不存
        FileResponse srcResponse = query(context, copyRequest.getSrcRequest());
        if (!srcResponse.isSuccess() || srcResponse.getUri() == null) {
            fileResponse.setSuccess(false);
            return fileResponse;
        }

        // 目标文件可能不存在,存在那就删除
        BaseRequest destFileRequest = copyRequest.getDestRequest();
        FileResponse destResponse = query(context, destFileRequest);
        if (destResponse.isSuccess()) {
            //存在,那么删除该文件
            FileResponse deleteResponse = delete(context, destFileRequest);
            if (!deleteResponse.isSuccess()) {
                deleteResponse.setSuccess(false);
                return deleteResponse;
            }
        }

        // 创建目标文件
        FileResponse newDestFile = newCreateFile(context, destFileRequest);
        Uri destUri = newDestFile.getUri();
        if (!newDestFile.isSuccess()) {
            newDestFile.setSuccess(false);
            return newDestFile;
        }

        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = context.getContentResolver().openOutputStream(destUri);
            inputStream = context.getContentResolver().openInputStream(srcResponse.getUri());
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(outputStream);
            BufferedInputStream fileInputStream = new BufferedInputStream(inputStream);

            byte[] buf = new byte[1024];
            while (fileInputStream.read(buf) != -1) {
                fileOutputStream.write(buf);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse query(Context context, T baseRequest) {
        Uri uri = uriMap.get(baseRequest.getUriType());
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        String selection = "1=1 and _display_name =?";
        String[] selectionArgs = {baseRequest.getDisplayName()};
        Cursor cursor = null;
        try {
            // 注意不要使用relative_path 这样是查不到数据的，一般就通过文件名即可查询到数据
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri queryUri = null;
        if (cursor != null && cursor.moveToFirst()) {
            queryUri = ContentUris.withAppendedId(uri, cursor.getLong(0));
            cursor.close();
        }


        FileResponse fileResponse = new FileResponse(context.getContentResolver());
        if (queryUri != null) {
            fileResponse.setUri(queryUri);
            fileResponse.setSuccess(true);
        } else {
            fileResponse.setSuccess(false);
        }
        return fileResponse;
    }
}
