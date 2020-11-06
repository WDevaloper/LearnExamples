package com.github.netloader.retrofit.plus;

import androidx.annotation.Nullable;

import com.github.netloader.net.GET;
import com.github.netloader.retrofit.annotation.DELETE;
import com.github.netloader.retrofit.annotation.POST;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class ServiceMethodPlus<T> {

    public static ServiceMethodPlus<?> parseAnnotations(RetrofitPlus retrofitPlus, Method method) {
        Annotation[] methodAnnotations = method.getAnnotations();
        for (Annotation annotation : methodAnnotations) {
            parseMethodAnnotations(annotation);
        }

        return null;
    }

    private static void parseMethodAnnotations(Annotation annotation) {
        if (annotation instanceof DELETE) {
            parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
        } else if (annotation instanceof GET) {
            parseHttpMethodAndPath("GET", ((retrofit2.http.GET) annotation).value(), false);
        } else if (annotation instanceof POST) {
            parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
        }
    }

    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");

    private static void parseHttpMethodAndPath(String method, String value, boolean hasBody) {

    }


    @Nullable
    abstract T invoke(Object[] args);
}
