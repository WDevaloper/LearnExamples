package com.github.binderlearn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestService extends Service {
    private RemoteCallbackList<CallbacklInterface> callbackList = new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int size = callbackList.beginBroadcast();
                for (int i = 0; i < size; i++) {
                    try {
                        CallbacklInterface callbackItem = callbackList.getBroadcastItem(i);
                        callbackItem.onCallback(99999);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                callbackList.finishBroadcast();
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        User test = intent.getExtras().getParcelable("test");
        Log.e("tag", test.name);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ITestAidlInterface.Stub() {
            @Override
            public void request(CallbacklInterface callback) throws RemoteException {
                callbackList.register(callback);
            }

            @Override
            public void destroy(CallbacklInterface callback) throws RemoteException {
                callbackList.register(callback);
            }
        };
    }
}
