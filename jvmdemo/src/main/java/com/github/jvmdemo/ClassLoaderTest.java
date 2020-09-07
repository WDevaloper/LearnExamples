package com.github.jvmdemo;

public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();
        System.out.println(loader);
        while (loader != null) {
            loader = loader.getParent();
            System.out.println(loader);
        }
    }
}
