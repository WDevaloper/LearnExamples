package com.github.plug_in;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.plugintstand.Constants;
import com.github.plugintstand.ServiceInterface;

import java.lang.reflect.Constructor;

public class ProxyService extends Service {

    private ServiceInterface serviceInterface;

    @Override
    public boolean onUnbind(Intent intent) {
        return serviceInterface.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String className = intent.getStringExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME);
            Class<?> aPluginServiceClass = getClassLoader().loadClass(className);
            Constructor<?> aConstructor = aPluginServiceClass.getConstructor(new Class[]{});
            Object instance = aConstructor.newInstance();
            serviceInterface = (ServiceInterface) instance;
            serviceInterface.injectHostEnvForPlugin(this);
            serviceInterface.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceInterface.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        serviceInterface.onDestroy();
        super.onDestroy();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }


    @Override
    public ComponentName startService(Intent service) {
        String className = service.getStringExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME);
        Intent newIntent = new Intent();
        newIntent.putExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME, className);
        return super.startService(newIntent);
    }
}
