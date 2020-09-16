package com.github.jvmdemo;

public class JvmTest3 {
    public static void main(String[] args) {
        int identityHashCode = System.identityHashCode(Singletion.getInstance());
        System.out.println("identityHashCode = " + identityHashCode);
    }
}
