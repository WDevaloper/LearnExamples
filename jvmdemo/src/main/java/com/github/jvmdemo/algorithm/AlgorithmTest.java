package com.github.jvmdemo.algorithm;


//算法

/**
 * 解法：用两个指针，一个指向字符串的第一个字符 a，一个指向它的最后一个字符 m，然后互相交换。
 * 交换之后，两个指针向中央一步步地靠拢并相互交换字符，直到两个指针相遇。这是一种比较快速和直观的方法。
 * <p>
 * 注意：由于无法直接修改字符串里的字符，所以必须先把字符串变换为数组，然后再运用这个算法。
 */
public class AlgorithmTest {
    //    final static String test = "algorithm test";
    final static String test = "AlgorithmTest";

    public static void main(String[] args) {
        char[] chars = test.toCharArray();
        char one, two;

        for (int i = 0; i < chars.length; i++) {

            if (i == chars.length - 1 - i) break;

            one = chars[i];
            two = chars[chars.length - 1 - i];

            chars[i] = two;
            chars[chars.length - 1 - i] = one;
        }

        for (char aChar : chars) {
            System.out.println("aChar = " + aChar);
        }
    }
}
