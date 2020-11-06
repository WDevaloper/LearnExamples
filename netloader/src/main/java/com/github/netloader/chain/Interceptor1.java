package com.github.netloader.chain;


public class Interceptor1 implements Interceptor {

    @Override
    public Response proceed(Chain chain, String request) {
        System.out.println(">>>>>>>>>Interceptor1");
        return chain.proceed(request);
    }
}
