package com.gavin.asmdemo.lagou;

public class Foo {
    private int number;

    public void test() {
        int i = 0;
        synchronized (this) {
            number = i + 1;
        }
    }

    public static void main(String[] args) {
    }
}
