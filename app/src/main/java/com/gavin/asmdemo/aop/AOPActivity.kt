package com.gavin.asmdemo.aop

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gavin.asmdemo.R
import com.gavin.asmdemo.aop.User.testStatic

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


    fun testAopStatic(view: View) {
        val testStattic = TestStattic("obejct")
        testStatic("yyyyyyyyyymmmmmmmmmmmmmmmmmm", testStattic)
        testStattic.name = "对象"
        testStatic("yyyyyyyyyymmmmmmmmmmmmmmmmmm", testStattic)
    }
}
