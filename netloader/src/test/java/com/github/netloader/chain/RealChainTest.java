package com.github.netloader.chain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RealChainTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void proceed() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new Interceptor1());
        interceptors.add(new Interceptor2());
        interceptors.add(new Interceptor3());
        interceptors.add(new Interceptor4());


        Interceptor.Chain chain = new RealChain(interceptors, "chain");
        Response response = chain.proceed("hahah");
        System.out.println(""+response);
    }
}