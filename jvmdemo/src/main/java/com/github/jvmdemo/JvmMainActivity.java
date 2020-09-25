package com.github.jvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

import kotlin.HashCodeKt;

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

        // oopDesc中包含两个数据成员：_mark 和 _metadata。
        // 其中markOop类型的_mark对象指的是前面讲到的Mark World。_metadata是一个共用体，其中_klass是普通指针，
        // _compressed_klass是压缩类指针，它们就是前面讲到的元数据指针，这两个指针都指向instanceKlass对象，它用来描述对象的具体类型。

        // InstanceKlass继承klass， InstanceKlass是Java Class 的VM级别表示。
        // 它包含运行时在Class上所需的所有信息。
        JvmTest jvmTest = new JvmTest();
        jvmTest.test(1000);
        JvmTest jvmTest1 = new JvmTest();
        jvmTest1.test(400);

        try {
            OopKlassModel.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent activity", null);
        return super.dispatchTouchEvent(ev);
    }

    class Test extends HashMap<String, String> {

    }
}
