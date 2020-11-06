package com.github.netloader.chain;


public class Interceptor3 implements Interceptor{
    @Override
    public Response proceed(Chain chain, String request) {
        System.out.println(">>>>>>>>>Interceptor3");
        return chain.proceed(request);
    }
}
