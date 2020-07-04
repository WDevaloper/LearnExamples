package com.github.netloader;


public class RequestTest {

    public static void main(String[] args) {
        BannerRequest request = new BannerRequest.Builder()
                .age(222)
                .id("88999889")
                .build();
        System.out.println(request);
    }
}
