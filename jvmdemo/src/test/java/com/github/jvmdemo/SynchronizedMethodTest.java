package com.github.jvmdemo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SynchronizedMethodTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testSynchronized() {
        SynchronizedMethod method = new SynchronizedMethod();
        method.test();
        method.test2();
    }

}