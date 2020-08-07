package com.github.adapt_android_r.sanbox.uitls;

import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.github.adapt_android_r.sanbox.file.impl.MediaStoreAccessImp;
import com.github.adapt_android_r.sanbox.request.BaseRequest;

public class UriTypeUtil {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void setFileType(BaseRequest baseRequest) {
        if (baseRequest.getDisplayName().endsWith("mp3")
                || baseRequest.getDisplayName().endsWith(".wav")) {
            baseRequest.setUriType(MediaStoreAccessImp.AUDIO);
        } else if (baseRequest.getDisplayName().endsWith(".mp4")
                || baseRequest.getDisplayName().endsWith(".rmvb")
                || baseRequest.getDisplayName().endsWith(".avi")) {
            baseRequest.setUriType(MediaStoreAccessImp.VIDEO);
        } else if (baseRequest.getDisplayName().startsWith(MediaStoreAccessImp.IMAGE)
                || baseRequest.getDisplayName().endsWith(".jpg")
                || baseRequest.getDisplayName().endsWith(".png")
                || baseRequest.getDisplayName().endsWith(".jpeg")) {
            baseRequest.setUriType(MediaStoreAccessImp.IMAGE);
        } else {//图片是不能保存在这里的
            baseRequest.setUriType(MediaStoreAccessImp.DOWNLOADS);
        }
    }

    public static boolean isAndroidQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy();
    }
}
