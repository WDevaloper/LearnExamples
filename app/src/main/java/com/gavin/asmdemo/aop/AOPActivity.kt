package com.gavin.asmdemo.aop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gavin.asmdemo.R

class AOPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)
    }


    @VipCheck("svip")
    fun svip(view: View) {
        Log.e("tag", "svip")
    }

    @VipCheck("vip")
    fun vip(view: View) {

    }

    @LoginCheck(ILoginCheckImpl::class)
    @VipCheck("普通会员")
    fun pvip(view: View) {

    }

    @LoginCheck(ILoginCheckImpl::class)
    @VipCheck("优惠券")
    fun youhuijuan(view: View) {
    }
}
