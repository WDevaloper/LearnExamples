package com.github.jvmdemo;


public class JvmTest1 {

    public static void main(String[] args) {
        test(new Object());
    }

    public static void test(Object clazz) {
        final Object lock = new Object();


        Thread threadA = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("A wait.....");
                    synchronized (lock) {
                        lock.wait();
                    }
                    System.out.println("A wait finish.....");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread threadB = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    System.out.println("B sleep.....");
                    sleep(1000);
                    synchronized (lock) {
                        lock.notify();
                    }
                    System.out.println("B finish.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        threadA.start();
        threadB.start();
    }
}