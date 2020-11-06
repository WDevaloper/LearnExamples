package com.github.netloader.chain;


public interface Interceptor {
    Response proceed(Chain chain,String request);

    interface Chain {
        Response proceed(String request);
    }
}
