package com.github.aop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.StaticLayout
import android.util.Log
import android.view.View

class AopMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop_main)
    }

    fun aopArgsClick(view: View) {
        val aopArgsTest = AopArgsTest()
        aopArgsTest.argsMethod1("hello")
    }

    fun joinPointTestConstruct(view: View) {
        JoinPointStudyTestClass()
    }

    fun joinPointTestFiled(view: View) {
        JoinPointStudyTestClass().also {
            it.name = "writer"
        }.apply {
            Log.e("tag", "joinPointTestFiled: ${name}")
        }
    }

    fun testMethod(view: View) {
        JoinPointStudyTestClass().test()
    }
}
