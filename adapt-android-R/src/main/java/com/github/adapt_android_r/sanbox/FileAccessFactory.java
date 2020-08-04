package com.github.adapt_android_r.sanbox;

import android.os.Build;
import android.os.Environment;

import com.github.adapt_android_r.sanbox.impl.FileStoreImpl;
import com.github.adapt_android_r.sanbox.impl.MediaStoreAccessImp;


public class FileAccessFactory {
    public static IFile getIFile(BaseRequest baseRequest) {
// Android 10.0    10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                !Environment.isExternalStorageLegacy()) {
            setFileType(baseRequest);
//            Android 11
            return MediaStoreAccessImp.getInstance();
        } else {
            return new FileStoreImpl();
        }
    }

    private static void setFileType(BaseRequest request) {
        if (request.getFile().getAbsolutePath().endsWith("mp3") ||
                request.getFile().getAbsolutePath().endsWith(".wav")) {
            request.setType(MediaStoreAccessImp.AUDIO);
        } else if (request.getFile().getAbsolutePath().startsWith(MediaStoreAccessImp.VIDEO)
                || request.getFile().getAbsolutePath().endsWith(".mp4")
                || request.getFile().getAbsolutePath().endsWith(".rmvb")
                || request.getFile().getAbsolutePath().endsWith(".avi")) {
            request.setType(MediaStoreAccessImp.VIDEO);
        } else if (request.getFile().getAbsolutePath().startsWith(MediaStoreAccessImp.IMAGE)
                || request.getFile().getAbsolutePath().endsWith(".jpg")
                || request.getFile().getAbsolutePath().endsWith(".png")) {
            request.setType(MediaStoreAccessImp.IMAGE);
        } else {
            request.setType(MediaStoreAccessImp.DOWNLOADS);
        }
    }

}
