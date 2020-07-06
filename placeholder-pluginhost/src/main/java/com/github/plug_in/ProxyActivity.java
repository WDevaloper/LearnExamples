package com.github.plug_in;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.plugintstand.ActivityInterface;
import com.github.plugintstand.Constants;

import java.lang.reflect.Constructor;

/**
 * 代理Activity 占位/代理 插件里的Activity
 * <p>
 * <p>
 * 需要主要的是ProxyActivity 的launchModel只能是standard
 */
public class ProxyActivity extends Activity {
    private ActivityInterface activityInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String className = getIntent().getStringExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME);
            // 实际上是插件包中的Activity类名，拿到类名，那么久需要通过插件的类加载器完成加载
            Class<?> aPluginActivityClass = getClassLoader().loadClass(className);
            Constructor<?> constructor = aPluginActivityClass.getConstructor();
            Object instance = constructor.newInstance();
            activityInterface = (ActivityInterface) instance;

            //将宿主的代理Activity实例注入到插件中，即将宿主的运行环境注入到插件，提供给插件运行环境
            activityInterface.injectHostEnvForPlugin(this);

            Bundle bundle = new Bundle();
            bundle.putString("appName", "只是宿主传过来的数据");
            activityInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityInterface.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityInterface.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInterface.onDestroy();
    }

    /**
     * 这个Resources是专门设计用来加载插件资源的
     *
     * @return Resources
     */
    @Override
    public Resources getResources() {
        Log.e("tag", this + " Load Plugin Resources  For getResources");
        return PluginManager.getInstance().getResources();
    }


    /**
     * 这个ClassLoader 是我们设计用来加载插件包中的类加载器
     *
     * @return ClassLoader
     */
    @Override
    public ClassLoader getClassLoader() {
        Log.e("tag", this + " Load Plugin Class For getClassLoader");
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME);
        Intent newIntent = new Intent(this, ProxyActivity.class);
        newIntent.putExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME, className);
        super.startActivity(newIntent);
    }
}
