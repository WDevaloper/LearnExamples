package com.github.ioc;

import android.util.Log;

public class IProxyImpl implements IProxy {
    @Override
    public void sayHello() {
        Log.e("tag", "hello proxy");
    }
}
