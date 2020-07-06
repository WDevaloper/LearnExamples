package com.github.plugintaopp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.github.plugintstand.PluginStandardConstants;
import com.github.plugintstand.ServicePluginStandardInterface;

public class BasePluginService extends Service implements ServicePluginStandardInterface {
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
            String className = service.getStringExtra(PluginStandardConstants.PLUGIN_CLASS_NAME);
            Intent newIntent = new Intent();
            newIntent.putExtra(PluginStandardConstants.PLUGIN_CLASS_NAME, className);
            return hostService.startService(newIntent);
        }
        return super.startService(service);
    }


    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (hostService != null) {
            return hostService.registerReceiver(receiver, filter);
        }
        return super.registerReceiver(receiver, filter);
    }


    @Override
    public void sendBroadcast(Intent intent) {
        if (hostService != null) {
            Intent newIntent = new Intent();
            newIntent.putExtra(PluginStandardConstants.PLUGIN_CLASS_NAME, intent.getComponent().getClassName());
            hostService.sendBroadcast(newIntent);
            return;
        }
        super.sendBroadcast(intent);
    }
}
