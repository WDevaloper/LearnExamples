package com.github.binderlearn;

import android.app.Application;
import android.util.Log;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "app onCreate");
    }
}
