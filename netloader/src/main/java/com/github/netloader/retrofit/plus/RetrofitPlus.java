package com.github.netloader.retrofit.plus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RetrofitPlus {

    private final Map<Method, ServiceMethodPlus<?>> serviceMethodCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(
                service.getClassLoader(),
                new Class[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return loadServiceMethod(method).invoke(args);
                    }
                }
        );
    }

    private ServiceMethodPlus<?> loadServiceMethod(Method method) {
        ServiceMethodPlus<?> result = serviceMethodCache.get(method);
        if (result != null) return result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = ServiceMethodPlus.parseAnnotations(this, method);
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }
}
