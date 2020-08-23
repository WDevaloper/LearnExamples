package com.github.apm;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.facebook.stetho.Stetho;


public class PerformanceApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LauncherTimer.startRecord();
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
