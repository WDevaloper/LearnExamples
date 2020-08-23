package com.github.apm;

import android.util.Log;

public class LauncherTimer {
    private static long sTime;

    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }

    public static void endRecord(String timeName) {
        long cost = System.currentTimeMillis() - sTime;
        Log.e("tag", timeName + " cost = " + cost);
    }
}
