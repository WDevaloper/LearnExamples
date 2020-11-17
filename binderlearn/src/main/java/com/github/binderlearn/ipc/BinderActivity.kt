package com.github.binderlearn.ipc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.binderlearn.R
import com.github.ipc.service.manager.core.RealBinder

open class BinderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)

        //binder_open
        RealBinder.open(this)

        //注册服务
        RealBinder.register(IPCServiceImpl::class.java)
    }

    fun jumpSecondActivity(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }
}