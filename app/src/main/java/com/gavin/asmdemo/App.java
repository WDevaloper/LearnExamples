package com.gavin.asmdemo;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

import com.gavin.asmdemo.db.base.BaseDaoFactory;

import org.greenrobot.eventbus.EventBus;

import me.ele.lancet.base.Origin;
import me.ele.lancet.base.annotations.Proxy;
import me.ele.lancet.base.annotations.TargetClass;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseDaoFactory.init(this, "my_user.db");
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }

    @Proxy("applyDimension")
    @TargetClass("android.util.TypedValue")
    public static float anyName(int unit, float value, DisplayMetrics metrics) {
        Log.e("tag", "anyName");
        return (float) Origin.call() + 100f;
    }


    @Proxy("e")
    @TargetClass("android.util.Log")
    public static int anyName(String log, String msg) {
//        msg += "lancet";
        return (int) Origin.call();
    }
}
