package com.github.aop

import android.util.Log

class JoinPointStudyTestClass {

    companion object {
        private val TAG: String? = "tag"

        init {
            Log.e(TAG, "Static")
        }
    }

    var name: String = ""

    constructor() {
        Log.e(TAG, "AopStudyTestClass:constructor()")
    }


    fun test() {
        Log.e(TAG, "test")
    }
}