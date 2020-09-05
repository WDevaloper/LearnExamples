package com.github.skin_core;

import android.app.Application;
import android.util.Log;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String resourcePackageName = getBaseContext().getResources().getResourceName(R.style.AppTheme);
        int appTheme = getResources().getIdentifier("AppTheme", "style", "com.github.skin_core");


        Log.e("tag", appTheme + " >>>>>>>>>>>>>>>" + resourcePackageName);
    }
}
