package com.gavin.asmdemo.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature


/**
 * AspectJ 完成AOP编程，即：面向切面编程
 *
 * 可用于统计用户行为、埋点
 *
 *
 *
 * LoginCheckAspect就是一个单例对象，管理者这些切点
 */
@Aspect
class LoginCheckAspect {

    private var vcount: Int = 0

    @Pointcut("execution(@com.gavin.asmdemo.aop.LoginCheck * *(..))")
    fun vipCheck() {
    }

    @Throws(Throwable::class)
    @Around("vipCheck()")
    fun aroundJointPoint(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature


        // 获取这个方法处于哪个类型
        val className = signature.declaringType.simpleName

        val value = signature.method.getAnnotation(LoginCheck::class.java).value
        Log.e("tag", "${vcount++}")


        //每次都会创建一个对象，希望 可以缓存
        val instance = value.constructors.first().call()
        val doCheck = instance.doCheck()

        Log.e("tag", "$className    $value        $doCheck       $instance")

        val startTime = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        Log.e("tag", "cost time: ${System.currentTimeMillis() - startTime}")
        return proceed
    }
}