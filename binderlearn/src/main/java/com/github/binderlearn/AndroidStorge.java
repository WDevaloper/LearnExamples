package com.github.binderlearn;


import android.net.Uri;

public interface AndroidStorge {
    Uri insert(String imageName, String imageType, String relativePath);
}
