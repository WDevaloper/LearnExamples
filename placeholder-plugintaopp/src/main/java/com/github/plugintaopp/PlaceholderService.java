package com.github.plugintaopp;

import android.content.Intent;
import android.util.Log;

public class PlaceholderService extends BasePluginService {

    private String TAG = PlaceholderService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, this + " Plugin Service onCreate");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, " Plugin Service 执行耗时操作");

                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, this + " Plugin Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, this + " Plugin Service onDestroy");
        super.onDestroy();
    }
}
