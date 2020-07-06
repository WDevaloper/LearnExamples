package com.github.plugintaopp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.plugintstand.ReceiverInterface;

public abstract class BaseReceiver extends BroadcastReceiver implements ReceiverInterface {
    private BroadcastReceiver receiver;

    @Override
    public void injectHostEnvForPlugin(BroadcastReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
