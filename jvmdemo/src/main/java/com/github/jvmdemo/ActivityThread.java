package com.github.jvmdemo;

import android.os.AsyncTask;
import android.os.Binder;

import androidx.annotation.Nullable;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// https://tech.meituan.com/2018/11/15/java-lock.html
//y s 不会释放锁  wait会释放锁
public class ActivityThread {
    public static void main(String[] args) {
        UseThread useThread = new UseThread();
        useThread.start();
    }

    static int count = 0;

    private static synchronized void test() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        useThreadLocal.set(100);
        Integer sInteger = useThreadLocal.get();
        System.out.println("sInteger = " + sInteger);
        useThreadLocal.remove();
        Integer sInteger2 = useThreadLocal.get();
        System.out.println("sInteger2 = " + sInteger2);
        if (count++ == 0) {
            test();
        }
    }


    private static UseThreadLocal useThreadLocal = new UseThreadLocal();
    private static Object obj = new Object();

    //可以分为公平和不公平锁
    private static Lock lock = new ReentrantLock();

    static class UseThread extends Thread {
        @Override
        public void run() {
            test();
        }
    }

    static class UseThreadLocal extends ThreadLocal<Integer> {
        @Nullable
        @Override
        protected Integer initialValue() {
            return 1;
        }
    }
}
