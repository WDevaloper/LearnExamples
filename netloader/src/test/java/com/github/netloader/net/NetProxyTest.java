package com.github.netloader.net;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Response;

public class NetProxyTest {

    @Test
    public void get() throws IOException {
        Api api = new NetProxy().createApi(Api.class);
        Response baiDu = api.getBaiDu();
        System.out.println(baiDu.body().string());
    }
}