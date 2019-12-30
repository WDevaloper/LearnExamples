package com.gavin.asmdemo.aop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import com.gavin.asmdemo.R
import me.ele.lancet.base.Origin
import me.ele.lancet.base.annotations.Proxy
import me.ele.lancet.base.annotations.TargetClass

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

    /**
     * 检查是否登录，并跳转登录页面
     */
    @LoginCheck(ILoginCheckImpl::class, "/user/login_activity")
    @VipCheck("优惠券")
    fun youhuijuan(view: View) {
    }
}
