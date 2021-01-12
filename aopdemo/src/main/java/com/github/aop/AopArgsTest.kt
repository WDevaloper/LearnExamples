package com.github.aop

import android.util.Log

class AopArgsTest {

    fun argsMethod1(args1: String) {
        Log.e(Constants.TAG, ">>argsMethod1")
    }

    fun argsMethod2(args1: String, args2: Int) {
        Log.e(Constants.TAG, ">>argsMethod2")
    }


    fun argsMethod3(args1: Int, args2: Int): Int {
        Log.e(Constants.TAG, ">>argsMethod3")
        return args1.plus(args2)
    }


}