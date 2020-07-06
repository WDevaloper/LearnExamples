package com.github.plugintaopp;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.github.plugintstand.Constants;
import com.github.plugintstand.ServiceInterface;

public class BaseService extends Service implements ServiceInterface {
    //宿主环境
    private Service hostService;

    @Override
    public void injectHostEnvForPlugin(Service hostService) {
        this.hostService = hostService;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }


    @Override
    public void onDestroy() {
    }


    @Override
    public ComponentName startService(Intent service) {
        if (hostService != null) {
            String className = service.getStringExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME);
            Intent newIntent = new Intent();
            newIntent.putExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME, className);
            return hostService.startService(newIntent);
        }
        return super.startService(service);
    }
}
