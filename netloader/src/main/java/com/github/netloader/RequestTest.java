package com.github.netloader;


import retrofit2.Retrofit;

public class RequestTest {

    public static void main(String[] args) {
        BannerRequest request = new BannerRequest.Builder()
                .age(222)
                .id("88999889")
                .build();
        System.out.println(request);
    }

}

