package com.gavin.asmdemo.mvvm

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gavin.asmdemo.R
import com.gavin.asmdemo.databinding.MvvmBind

class MvvmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MvvmBind = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)

        val user = User()
        user.firstName = "9999"
        binding.layout.bindData(user)

        Handler().postDelayed({
            user.firstName = "66666"
        }, 2000)
    }
}
