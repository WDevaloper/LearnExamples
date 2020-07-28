package com.wfy.hook_pluginhost;

import android.app.Application;

import java.lang.reflect.Method;

public class HookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        hook();
    }

    private void hook() {
        try {


            Class<?> singletonClass = Class.forName("android.util.Singleton");

            // Android Q才引进来的类
            //加载 Ac
            // tivityTaskManager
            Class<?> atmClass = Class.forName("android.app.ActivityTaskManager");//Andorid 10才引进来的API
            // 调用ActivityTaskManager的静态方法getService得到ActivityTaskManager实例对象
            Method getServiceMethod = atmClass.getMethod("getService", new Class[]{});
            getServiceMethod.setAccessible(true);
            Object atmInstance = getServiceMethod.invoke(null, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
