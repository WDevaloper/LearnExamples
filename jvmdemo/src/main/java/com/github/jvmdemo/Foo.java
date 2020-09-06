package com.github.jvmdemo;

public class Foo {
    private int number;

    public void test1() {
        int i = 0;
        synchronized (this) {
            number = i + 1;
        }
    }
}
