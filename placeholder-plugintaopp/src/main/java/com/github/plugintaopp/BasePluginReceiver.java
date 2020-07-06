package com.github.plugintaopp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.plugintstand.ReceiverPluginStandardInterface;

public abstract class BasePluginReceiver extends BroadcastReceiver implements ReceiverPluginStandardInterface {
    private BroadcastReceiver receiver;

    @Override
    public void injectHostEnvForPlugin(BroadcastReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}
