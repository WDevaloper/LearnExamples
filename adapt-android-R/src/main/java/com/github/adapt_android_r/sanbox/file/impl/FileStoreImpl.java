package com.github.adapt_android_r.sanbox.file.impl;

import android.Manifest;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.RequiresPermission;

import com.github.adapt_android_r.sanbox.file.FileCallback;
import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.request.impl.WrapperRequest;
import com.github.adapt_android_r.sanbox.response.FileResponse;
import com.github.adapt_android_r.sanbox.file.IFile;
import com.github.adapt_android_r.sanbox.uitls.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

//Android10一下
public class FileStoreImpl implements IFile {
    //    Android 9  8
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @Override
    public <T extends BaseRequest> FileResponse newCreateFile(Context context, T baseRequest) {
        FileResponse queryResp = query(context, baseRequest);
        try {
            if (!queryResp.isSuccess()) {
                File file = new File(baseRequest.getPath(), baseRequest.getDisplayName());
                file.getParentFile().mkdirs();
                boolean isSuccess = file.createNewFile();
                queryResp.setSuccess(isSuccess);
                queryResp.setUri(Uri.fromFile(file));
                queryResp.setFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryResp;
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @Override
    public <T extends BaseRequest> FileResponse delete(Context context, T baseRequest) {
        FileResponse queryResp = query(context, baseRequest);
        if (queryResp.isSuccess()) {
            File file = new File(baseRequest.getPath(), baseRequest.getDisplayName());
            queryResp.setSuccess(file.delete());
        }
        return queryResp;
    }

    @Override
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public <T extends BaseRequest> void delete(Context context, T baseRequest, FileCallback callback) throws RuntimeException {
        FileResponse response = delete(context, baseRequest);
        callback.onCallback(response);
    }

    @Override
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public <T extends BaseRequest> FileResponse renameTo(Context context, T wrapperRequest) {
        WrapperRequest<T> tWrapperRequest = (WrapperRequest<T>) wrapperRequest;
        return renameTo(context, tWrapperRequest.getSrcRequest(), tWrapperRequest.getDestRequest());
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @Override
    public <T extends BaseRequest> FileResponse renameTo(Context context, T where, T request) {
        File srcFile = new File(where.getPath(), where.getDisplayName());

        FileResponse queryResp = query(context, where);

        if (!queryResp.isSuccess()) {
            return queryResp;
        }

        File destFile = new File(request.getPath(), request.getDisplayName());
        boolean isSuccess = srcFile.renameTo(new File(request.getPath(), request.getDisplayName()));
        queryResp.setSuccess(isSuccess);
        queryResp.setUri(Uri.fromFile(destFile));
        queryResp.setFile(destFile);
        return queryResp;
    }

    @Override
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public <T extends BaseRequest> FileResponse copyFile(Context context, T srcRequest, T destRequest) {
        WrapperRequest<T> tWrapperRequest = new WrapperRequest<>(srcRequest, destRequest);
        return copyFile(context, tWrapperRequest);
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @Override
    public <T extends BaseRequest> FileResponse copyFile(Context context, T baseRequest) {
        WrapperRequest<T> copyRequest = (WrapperRequest<T>) baseRequest;

        // 判断源文件存不存
        FileResponse srcQueryResp = query(context, copyRequest.getSrcRequest());
        if (!srcQueryResp.isSuccess()) {
            return srcQueryResp;
        }

        // 目标文件可能存在也可能不存在,存在那就删除
        FileResponse destQueryResp = query(context, copyRequest.getDestRequest());
        if (destQueryResp.isSuccess()) {
            //存在,那么删除该文件
            destQueryResp = delete(context, copyRequest.getDestRequest());
            if (!destQueryResp.isSuccess()) {
                return destQueryResp;
            }
        }

        // 创建目标文件
        FileResponse newDestFile = newCreateFile(context, copyRequest.getDestRequest());
        Uri destUri = newDestFile.getUri();
        if (!newDestFile.isSuccess()) {
            newDestFile.setSuccess(false);
            return newDestFile;
        }

        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            OutputStream outputStream = context.getContentResolver().openOutputStream(destUri);
            InputStream inputStream = context.getContentResolver().openInputStream(srcQueryResp.getUri());
            bos = new BufferedOutputStream(outputStream);
            bis = new BufferedInputStream(inputStream);

            byte[] buf = new byte[1024];
            while (bis.read(buf) != -1) {
                bos.write(buf);
            }
            newDestFile.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.closeIO(bis);
            Util.closeIO(bos);
        }

        return newDestFile;
    }

    @Override
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public <T extends BaseRequest> FileResponse query(Context context, T baseRequest) {
        File file = new File(baseRequest.getPath(), baseRequest.getDisplayName());
        FileResponse response = new FileResponse(context.getContentResolver());
        response.setSuccess(file.exists());
        response.setFile(file);
        response.setUri(Uri.fromFile(file));
        return response;
    }
}
