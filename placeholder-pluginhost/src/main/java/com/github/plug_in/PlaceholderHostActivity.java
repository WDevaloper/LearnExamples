package com.github.plug_in;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.github.plugintstand.PluginStandardConstants;

import java.io.File;


public class PlaceholderHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeholder_activity_plugin);
    }

    public void loadPlugin(View view) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
        PluginManager.getInstance().loadPlugin(this, file.getAbsolutePath());
    }


    //宿主启动插件的Activity
    public void startPluginActivity(View view) {
        //获取插件里的Activity
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
        //插件路径
        String pluginPath = file.getAbsolutePath();
        Log.e("tag", "pluginPath" + pluginPath);
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(pluginPath, PackageManager.GET_ACTIVITIES);
        ActivityInfo activityInfo = null;
        if (packageInfo != null) {
            //实际上是在mmanifest文件解析中的第一个Activity节点的Activity
            activityInfo = packageInfo.activities[0];
        }


        if (activityInfo == null) {
            Toast.makeText(this, "无法获取插件中的Activity", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ProxyActivity.class);
        //将插件中的Activity类的全路径传过去
        intent.putExtra(PluginStandardConstants.PLUGIN_CLASS_NAME, activityInfo.name);
        startActivity(intent);
    }

    public void startPluginService(View view) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
        String pluginPath = file.getAbsolutePath();
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(pluginPath, PackageManager.GET_SERVICES);
        ServiceInfo serviceInfo = null;
        if (packageInfo != null) {
            serviceInfo = packageInfo.services[0];
        }


        if (serviceInfo == null) {
            Toast.makeText(this, "无法获取插件中的Service", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ProxyService.class);
        //将插件中的Activity类的全路径传过去
        intent.putExtra(PluginStandardConstants.PLUGIN_CLASS_NAME, serviceInfo.name);
        startService(intent);
    }

    public void registerStaticReceiver(View view) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin.apk");
        PluginManager.getInstance().loadReceiverForPlugin(this, file);
    }

    public void sendStaticReceiver(View view) {
        Intent intent = new Intent("ACTION.StaticReceiver");
        sendBroadcast(intent);
    }
}