package com.github.jvmdemo;

//https://tech.meituan.com/2018/11/15/java-lock.html
public class ActivityThread {
    int mainM = 10;
    public static void main(String[] args) {
        final int aInt = 0;
        new Thread() {
            @Override
            public void run() {

            }
        };
    }


    public void test() {
        int b = 0;
    }
}
