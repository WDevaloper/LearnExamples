package com.github.ioc;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {
    //被代理对象
    private IProxyImpl object;

    public ProxyInvocationHandler(IProxyImpl object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.e("tag", "invoke: " + "before");
        return method.invoke(object, objects);
    }
}
