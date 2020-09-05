package com.github.ioc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.lang.reflect.Proxy;

public class IocMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioc_main);
        IProxyImpl iProxy = new IProxyImpl();
        IProxy instance = (IProxy) Proxy.newProxyInstance(iProxy.getClass().getClassLoader(),
                new Class[]{IProxy.class}, new ProxyInvocationHandler(iProxy));
        instance.sayHello();
    }
}
