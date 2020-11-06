package com.github.netloader.net;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetProxy {

    @SuppressWarnings("unchecked")
    public <T> T createApi(final Class<T> service) {
        return (T) Proxy.newProxyInstance(
                service.getClassLoader(),
                new Class<?>[]{service},
                new InvocationHandler() {
                    private final Object[] emptyArgs = new Object[0];

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        args = args != null ? args : emptyArgs;
                        GET get = method.getAnnotation(GET.class);
                        String url = get.value();
                        System.out.println(url);


                        List<String> paramNames = new LinkedList<>();
                        Annotation[][] paramAnnotations = method.getParameterAnnotations();
                        for (Annotation[] paramAnnotation : paramAnnotations) {
                            for (Annotation annotation : paramAnnotation) {
                                if (annotation.annotationType() == Query.class) {
                                    Query query = (Query) annotation;
                                    paramNames.add(query.value());
                                }
                            }
                        }

                        StringBuffer sb = new StringBuffer("?");
                        for (int i = 0; i < paramNames.size(); i++) {
                            sb.append(paramNames.get(i)).append("=").append(args[i]);
                            if (i != paramNames.size() - 1) {
                                sb.append("&");
                            }
                        }
                        Log.e("tag", sb.toString());
                        return parseRequest(url);
                    }
                });
    }

    private Object parseRequest(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClient.newCall(request);
        return call.execute();
    }
}
