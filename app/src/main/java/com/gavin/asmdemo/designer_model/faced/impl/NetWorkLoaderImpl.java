package com.gavin.asmdemo.designer_model.faced.impl;

import com.gavin.asmdemo.designer_model.faced.NetWorkLoader;

import java.io.InputStream;

public class NetWorkLoaderImpl implements NetWorkLoader {
    @Override
    public InputStream findNetWork(String url) {
        System.out.println("网络");
        return null;
    }
}
