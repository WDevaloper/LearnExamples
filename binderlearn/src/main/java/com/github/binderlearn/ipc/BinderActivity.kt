package com.github.binderlearn.ipc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.binderlearn.R

open class BinderActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)
    }

    fun jumpSecondActivity(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }
}