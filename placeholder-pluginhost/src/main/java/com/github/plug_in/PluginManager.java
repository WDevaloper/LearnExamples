package com.github.plug_in;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.UserHandle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class PluginManager {
    private static final PluginManager ourInstance = new PluginManager();
    private static String TAG = PluginManager.class.getSimpleName();

    private DexClassLoader dexClassLoader;
    private Resources resources;

    static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }


    /**
     * 加载插件
     *
     * @param context
     * @param path
     */
    public void loadPlugin(Context context, String path) {
        File apkFile = new File(path);
        if (!apkFile.exists()) {
            Log.e(TAG, "插件包不存在");
            return;
        }

        String dexPath = apkFile.getAbsolutePath();
        //dexClassLoader 缓存目录   /data/data/pDexCacheDir
        File pDexCacheDir = context.getDir("pDexCacheDir", Context.MODE_PRIVATE);

        dexClassLoader = new DexClassLoader(dexPath, pDexCacheDir.getAbsolutePath(),
                null, context.getClassLoader());
        //唯一的区别就是DexClassLoader构造参数
        //new PathClassLoader(dexPath,null,context.getClassLoader());

        /**
         * 加载layout资源
         *
         *
         * AssetManager.createSystemAssetsInZygoteLocked()
         */

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //把插件包添加进去
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            if (!addAssetPath.isAccessible()) {
                addAssetPath.setAccessible(true);
            }
            addAssetPath.invoke(assetManager, dexPath);

            //宿主的资源配置信息
            Resources hostResource = context.getResources();

            //特殊的Resources，加载插件里面的资源的 Resources
            this.resources = new Resources(assetManager, hostResource.getDisplayMetrics(), hostResource.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    Resources getResources() {
        return resources;
    }

    /**
     * 反射系统源码 来解析apk文件所有的信息
     *
     * @param apkFilePath 插件apk路径
     */
    @SuppressLint("PrivateApi")
    public void loadReceiverForPlugin(Context context, File apkFilePath) {
        try {
            //执行PackageParse.parsePackage方法解析apk信息
            Class<?> packageParseClass = Class.forName("android.content.pm.PackageParser");
            // 实例化执行PackageParse对象
            Object packageParse = packageParseClass.newInstance();
            //调用PackageParser对象的parsePackage去解析apk信息
            Method parsePackageMethod = packageParseClass.getMethod("parsePackage",
                    File.class, int.class, boolean.class);

            //加载Package类对内存
            Class<?> mPackageClass = Class.forName("android.content.pm.PackageParser$Package");
            //拿到Package对象
            Object mPackage =
                    parsePackageMethod.invoke(packageParse, apkFilePath, PackageManager.GET_ACTIVITIES, false);

            //获取Package中receivers filed   android.content.pm.PackageParser$Package.receivers
            Field receiversFiled = mPackageClass.getDeclaredField("receivers");
            //  public final ArrayList<Activity> receivers = new ArrayList<Activity>(0);
            // android.content.pm.PackageParser.Activity
            ArrayList receivers = (ArrayList) receiversFiled.get(mPackage);

            Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");

            Class<?> mPackageUserStateClass = Class.forName("android.content.pm.PackageUserState");

            Class<?> mUserHandlerClass = Class.forName("android.os.UserHandle");
            Method getCallingUserIdMethod = mUserHandlerClass.getMethod("getCallingUserId", null);
            for (Object activity : receivers) {
                // 获取intent filter intents == intent-filter
                // android.content.pm.PackageParser$Component.intents
                Field intentsFiled = componentClass.getDeclaredField("intents");
                ArrayList<IntentFilter> intents = (ArrayList<IntentFilter>) intentsFiled.get(activity);
                for (IntentFilter intentFilter : intents) {

                    int userId = (int) getCallingUserIdMethod.invoke(null);


                    // generateActivityInfo(Activity a, int flags,PackageUserState state, int userId)
                    Method generateActivityInfoMethod = packageParseClass.getMethod(
                            "generateActivityInfo", activity.getClass(),
                            int.class, mPackageUserStateClass, int.class);

                    ActivityInfo activityInfo =
                            (ActivityInfo) generateActivityInfoMethod.invoke(null, activity,
                                    0, mPackageUserStateClass.newInstance(), userId);

                    String mSpasticReceiverClassName = activityInfo.name;
                    Class<?> receiverClass = getDexClassLoader().loadClass(mSpasticReceiverClassName);
                    BroadcastReceiver receiver = (BroadcastReceiver) receiverClass.newInstance();

                    context.registerReceiver(receiver, intentFilter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
