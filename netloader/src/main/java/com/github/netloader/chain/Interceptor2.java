package com.github.netloader.chain;


public class Interceptor2 implements Interceptor{
    @Override
    public Response proceed(Chain chain, String request) {
        System.out.println(">>>>>>>>>Interceptor2");
        return chain.proceed(request);
    }
}
