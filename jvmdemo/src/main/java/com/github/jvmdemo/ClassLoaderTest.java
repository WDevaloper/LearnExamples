package com.github.jvmdemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        ClassLoader loader = HelloWorld.class.getClassLoader();
        System.out.println(loader);
        //使用ClassLoader.loadClass()来加载类，不会执行初始化块cinit 方法
        Class<?> aClass = loader.loadClass("com.github.jvmdemo.ClassLoaderBean");
        //使用Class.forName()来加载类，默认会执行初始化块
//        Class<?> aClass = Class.forName("com.github.jvmdemo.ClassLoaderBean");
        //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
//        Class<?> aClass = Class.forName("com.github.jvmdemo.ClassLoaderBean", false, loader);
        // hello world
        System.out.println("aClass = " + aClass);


        // 反射的特点   实际上是的过程是：字节码 ---（加载）》方法区 ---》链接（准备、验证、初始化） ----》
        //
        // 解析(将字符引用转为直接引用)执行  ，方法区中存储的字符引用 ，转为直接引用是在解析时分配引用内存空间
        Class<?> forName = Class.forName("com.github.jvmdemo.ClassLoaderTest");
        Method method = forName.getMethod("test", null);
        Object result = method.invoke(null, null);
        System.out.println("result = " + result);
    }

    public static String test() {
        System.out.println("test");
        return "test reflect";
    }
}

