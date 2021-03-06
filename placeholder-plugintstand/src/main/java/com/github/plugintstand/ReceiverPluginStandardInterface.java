package com.github.plugintstand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public interface ReceiverPluginStandardInterface {
    void injectHostEnvForPlugin(BroadcastReceiver receiver);

    void onReceive(Context context, Intent intent);
}
