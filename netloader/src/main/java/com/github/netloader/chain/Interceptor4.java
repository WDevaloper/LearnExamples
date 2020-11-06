package com.github.netloader.chain;


public class Interceptor4 implements Interceptor {

    @Override
    public Response proceed(Chain chain, String request) {
        System.out.println(">>>>>>>>>Interceptor4");
        return new Response();
    }
}
