package com.gavin.asmdemo.mvc.callback;


import com.gavin.asmdemo.mvc.bean.ImageBean;

public interface Callback {
    void callback(int resultCode, ImageBean imageBean);
}
