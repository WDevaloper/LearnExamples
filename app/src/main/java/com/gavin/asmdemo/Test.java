package com.gavin.asmdemo;

public class Test {

    public static void main(String[] args) {
        System.out.println("" + test());
    }

    public static int test() {
        int a = 0;
        try {
            a = 8;
            return a;
        } finally {
            return a;
        }
    }
}
