package com.github.plugintaopp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.plugintstand.ActivityInterface;
import com.github.plugintstand.Constants;

public class BaseActivity extends Activity implements ActivityInterface {
    protected Activity proxyHostActivity;//宿主环境


    @Override
    public void injectHostEnvForPlugin(Activity activity) {
        this.proxyHostActivity = activity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (proxyHostActivity == null) {
            super.onCreate(savedInstanceState);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {
        if (proxyHostActivity == null) {
            super.onResume();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {
        if (proxyHostActivity == null) {
            super.onStart();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {
        if (proxyHostActivity == null) {
            super.onDestroy();
        }
    }


    /**
     * 这里就直接换成了代理的Activity去加载Layout
     *
     * @param layoutResID layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        if (proxyHostActivity != null) {
            //处理作为插件的逻辑
            proxyHostActivity.setContentView(layoutResID);
            return;
        }
        super.setContentView(layoutResID);
    }


    /**
     * 如果不重写，那么在以插件的形式运行时，在ProxyActivity中是在不到这个View的，
     * 因为一插件运行的方式是把主布局设置到了ProxyActivity上的，所以你必须重写
     *
     * @param id
     * @param <T>
     * @return
     */
    @Override
    public <T extends View> T findViewById(int id) {
        if (proxyHostActivity != null) {
            //处理作为插件的逻辑
            return proxyHostActivity.findViewById(id);
        }
        return super.findViewById(id);
    }

    /**
     * 插件内部启动Activity
     * <p>
     * 你想想阿，因为插件本身就是没有运行环境，所以还是需要借助宿主的ProxyActivity完成Activity的入栈操作
     *
     * @param intent Intent
     */
    @Override
    public void startActivity(Intent intent) {
        if (proxyHostActivity != null) {
            //处理作为插件的逻辑
            //重新构造Intent，是为了传入className，提供给给宿主的ProxyActivity使用的
            Intent newIntent = new Intent();
            newIntent.putExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME, intent.getComponent().getClassName());
            //又得重写ProxyActivity去接收数据并启动自己
            proxyHostActivity.startActivity(newIntent);
            return;
        }
        super.startActivity(intent);
    }


    /**
     * 插件内部启动Service
     *
     * @param service Intent
     * @return
     */
    @Override
    public ComponentName startService(Intent service) {
        if (proxyHostActivity != null) {
            Intent newIntent = new Intent();
            newIntent.putExtra(Constants.PLUGIN_ACTIVITY_CLASS_NAME, service.getComponent().getClassName());
            return proxyHostActivity.startService(newIntent);
        }
        return super.startService(service);
    }


    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (proxyHostActivity != null) {
            return proxyHostActivity.registerReceiver(receiver, filter);
        }
        return super.registerReceiver(receiver, filter);
    }


    @Override
    public void sendBroadcast(Intent intent) {
        if (proxyHostActivity != null) {
            proxyHostActivity.sendBroadcast(intent);
            return;
        }
        super.sendBroadcast(intent);
    }

    public Context getActivity() {
        if (proxyHostActivity != null) {
            return proxyHostActivity;
        }
        return this;
    }
}
