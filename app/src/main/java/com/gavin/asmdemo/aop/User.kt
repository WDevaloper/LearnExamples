package com.gavin.asmdemo.aop

import android.util.Log

object User {
    @JvmStatic
    fun testStatic(str: String, obj: TestStattic) {
        Log.e("tag", "------->test aop static method!$str")
    }
}

class TestStattic(var name: String) {

    override fun toString(): String {
        return "TestStattic>>>>>>>toString:$name"
    }
}