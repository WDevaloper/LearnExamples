package com.github.binderlearn.ipc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.binderlearn.R
import com.github.ipc.service.manager.core.RealBinder

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scond)

        //打开binder
        RealBinder.open(this)

        // 获取服务
    }
}