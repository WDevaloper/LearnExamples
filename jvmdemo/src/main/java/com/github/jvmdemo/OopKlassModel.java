package com.github.jvmdemo;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Java对象模型  oop-klass
 * <p>
 * <p>
 * Java中类基本都对应着Jvm中C++类或结构体
 * <p>
 * oppKlassDesc  ---->  instanceKlass
 */
public class OopKlassModel {
    public static int a = 1;
    private int b;

    public OopKlassModel(int b) {
        this.b = b;
    }

    public static void main(String[] args) {
        int c = 10;
        OopKlassModel modelA = new OopKlassModel(2);
        OopKlassModel modelB = new OopKlassModel(3);
        System.out.println("args = " + Arrays.deepToString(args));


        // klass
        Class<OopKlassModel> aClass = modelB.getClass();
        Method method = aClass.getMethod("test", null);
        Object result = method.invoke(modelB, null);
    }

    public void test() {
        System.out.println("test");
    }
}
