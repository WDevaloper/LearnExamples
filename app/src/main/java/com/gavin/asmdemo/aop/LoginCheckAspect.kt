package com.gavin.asmdemo.aop

import android.util.Log
import org.aspectj.lang.JoinPoint
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
 * LoginCheckAspect就是一个单例对象，管理者这些切点
 *
 *
 *
 * 1、登录可以通过ARouter进行分装，外面直接在注解中传入Arouter path即可；
 * 2、当然也可以传入实现了ILoginCheck的实现类的Class，这种方案使用了反射；
 * 3、如果传两种默认使用第一种方案；
 *
 */
@Aspect

class LoginCheckAspect {
    // 访问次数
    private var vcount: Int = 0

    @Pointcut("execution(@com.gavin.asmdemo.aop.LoginCheck * *(..))")
    fun vipCheck() {
    }

    @Throws(Throwable::class)
    @Around("vipCheck()")
    fun aroundJointPoint(joinPoint: ProceedingJoinPoint): Any? {

        val signature = joinPoint.signature as MethodSignature

        // 获取这个方法处于哪个类型中定义
        val className = signature.declaringType.simpleName
        // 获取对应的注解
        val annotation = signature.method.getAnnotation(LoginCheck::class.java)
        val value = annotation.value
        val arouterPath = annotation.arouterPath
        Log.e("tag", String.format("Call Count:%d,Class Name: %s,ARouter Path: %s", vcount++, className, arouterPath))

        //每次都会创建一个对象，希望 可以缓存
        val instance = value.constructors.first().call()
        val doCheck = instance.doCheck()
        Log.e("tag", String.format("doCheck： %b", doCheck))

        //统计方法耗时
        val startTime = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        val enTime = System.currentTimeMillis() - startTime
        Log.e("tag", String.format("Cost Time:%d", enTime))
        return proceed
    }
}