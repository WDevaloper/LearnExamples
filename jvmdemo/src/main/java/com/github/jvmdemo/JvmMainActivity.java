package com.github.jvmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Method;
import java.util.HashMap;

// 几篇文章
// jvm运行时内存数据区域（JVM书+拉钩+掘金） ---> 字节码文件结构（JVM书+拉钩+掘金） ---> 类加载机制（JVM书+拉钩+掘金）
// 内存分配和GC（JVM书+拉钩+掘金）
// JMM（JVM书+拉钩+掘金）
public class JvmMainActivity extends AppCompatActivity {

    private static final String TAG = "touch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jvm_main);
        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(1, 2);
        // 这两个对象的shadow$_klass_是一样的，即：Class在内存中值存在一份
        // shadow$_klass_ 就是Object的字段
        // 而Class 中又有classLoader字段表示，这个Class是由哪个classLoader加载的
        JvmTest jvmTest = new JvmTest();
        jvmTest.test(1000);
        JvmTest jvmTest1 = new JvmTest();
        jvmTest1.test(400);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent activity", null);
        return super.dispatchTouchEvent(ev);
    }

    class Test extends HashMap<String, String> {

    }
}
