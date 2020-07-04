package com.github.netloader;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        BannerRequest request = new BannerRequest.Builder()
                .age(222)
                .id("88999889")
                .build();
        System.out.println(request);
    }
}