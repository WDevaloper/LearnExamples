package com.github.plugintstand;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public interface ServicePluginStandardInterface {


    void injectHostEnvForPlugin(Service hostService);

    IBinder onBind(Intent intent);


    int onStartCommand(Intent intent, int flags, int startId);


    boolean onUnbind(Intent intent);


    void onCreate();


    void onDestroy();
}
