package com.github.netloader.ui;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MyConverter extends Converter.Factory {

    @Override
    public Converter<?, RequestBody>
    requestBodyConverter(Type type,
                         Annotation[] parameterAnnotations,
                         Annotation[] methodAnnotations,
                         Retrofit retrofit) {


        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<ResponseBody, ?>
    responseBodyConverter(Type type,
                          Annotation[] annotations,
                          Retrofit retrofit) {
        return super.responseBodyConverter(type, annotations, retrofit);
    }
}
