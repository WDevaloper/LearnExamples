package com.github.plugintstand;

import android.app.Activity;
import android.os.Bundle;


/**
 * 占位式插件标准
 */
public interface ActivityPluginStandardInterface {

    /**
     * 把宿主环境注入到插件中
     *
     * @param activity 宿主activity
     */
    void injectHostEnvForPlugin(Activity activity);

    void onCreate(Bundle savedInstanceState);

    void onResume();

    void onStart();

    void onDestroy();
}
