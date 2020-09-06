package com.github.netloader.ui;

import java.io.IOException;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        if (method.equalsIgnoreCase("GET") ||
                method.equalsIgnoreCase("DELETE")) {
            HttpUrl httpUrl = request.url();
            Set<String> parameterNames = httpUrl.queryParameterNames();
            for (String parameterName : parameterNames) {
                String parameterValue = httpUrl.queryParameter(parameterName);
            }

            HttpUrl.Builder builder = request.url().newBuilder();
            builder.addQueryParameter("sign", "");
        }

        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());


        return null;
    }
}
