package com.github.plugintaopp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PlaceholderReceiver extends BasePluginReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("tag", "plugin onReceive");
    }
}
