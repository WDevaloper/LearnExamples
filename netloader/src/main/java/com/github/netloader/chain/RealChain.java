package com.github.netloader.chain;

import java.util.List;

public class RealChain implements Interceptor.Chain {
    private final List<Interceptor> interceptors;
    private final String request;
    private int index = 0;

    public RealChain(List<Interceptor> interceptors, String request) {
        this.interceptors = interceptors;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    @Override
    public Response proceed(String request) {
        Interceptor interceptor = interceptors.get(index++);
        return interceptor.proceed(this, request);
    }
}
