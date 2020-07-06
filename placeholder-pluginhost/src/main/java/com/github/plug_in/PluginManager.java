package com.github.plug_in;


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

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
         */

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            //把插件包添加进去
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);

            //宿主的资源配置信息
            Resources hostResource = context.getResources();

            //特殊的Resources，加载插件里面的资源的 Resources
            this.resources = new Resources(assetManager, hostResource.getDisplayMetrics(), hostResource.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }
}
