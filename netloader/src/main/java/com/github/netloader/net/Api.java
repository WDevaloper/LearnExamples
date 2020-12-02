package com.github.netloader.net;


import okhttp3.Response;

/**
 * 由动态代理解析请求参数
 */
public interface Api {
    @GET(value = "http://qukufile2.qianqian.com/data2/pic/246679913/246679913.jpg@s_2,w_300,h_300")
    Response getBaiDu(@Query("path") String path, @Query("path1") String path1);
}
