package com.github.jvmdemo.algorithm.linkedlsit;

import com.github.jvmdemo.algorithm.stack.StackTest;

import org.junit.Test;

public class StackTestTest {

    @Test
    public void validCharacters() {
        StackTest stackTest = new StackTest();
        Boolean aBoolean = stackTest.validCharacters("()");
        System.out.println("aBoolean = " + aBoolean);
        Boolean bBoolean = stackTest.validCharacters("()[]{}");
        System.out.println("bBoolean = " + bBoolean);
        Boolean cBoolean = stackTest.validCharacters("(]");
        System.out.println("cBoolean = " + cBoolean);
        Boolean dBoolean = stackTest.validCharacters("([)]");
        System.out.println("dBoolean = " + dBoolean);
        Boolean eBoolean = stackTest.validCharacters("{[]}");
        System.out.println("eBoolean = " + eBoolean);
    }

    //[23, 25, 21, 19, 22, 26, 23]
    @Test
    public void dailyTemperatures() {
        StackTest stackTest = new StackTest();
        int[] dailytemperatures = new int[]{23, 25, 21, 19, 22, 26, 23};
        int[] ans = stackTest.dailyTemperatures(dailytemperatures);

        for (int an : ans) {
            System.out.println("an = " + an);
        }
    }

}