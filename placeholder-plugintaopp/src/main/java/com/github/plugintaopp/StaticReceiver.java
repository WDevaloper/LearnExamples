package com.github.plugintaopp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "插件中静态广播", Toast.LENGTH_SHORT).show();
    }
}
