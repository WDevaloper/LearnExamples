package com.github.adapt_android_r;


import android.net.Uri;

public interface AndroidStorge {
    Uri insert(String imageName, String imageType, String relativePath);
}
