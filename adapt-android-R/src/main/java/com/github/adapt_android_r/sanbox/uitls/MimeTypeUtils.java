package com.github.adapt_android_r.sanbox.uitls;

import android.webkit.MimeTypeMap;

public class MimeTypeUtils {

    private MimeTypeUtils() {
    }

    public static String getMimeType(final String fileName) {
        String result = "application/octet-stream";
        int extPos = fileName.lastIndexOf(".");
        if (extPos != -1) {
            String ext = fileName.substring(extPos + 1);
            result = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        }
        return result;
    }
}