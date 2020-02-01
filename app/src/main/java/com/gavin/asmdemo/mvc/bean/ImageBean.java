package com.gavin.asmdemo.mvc.bean;

import android.graphics.Bitmap;

public class ImageBean {
    private String requestPath;
    private Bitmap bitmap;


    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
