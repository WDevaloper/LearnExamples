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
 */
@Aspect
class VipCheckAspect {

    @Pointcut("execution(@com.gavin.asmdemo.aop.VipCheck * *(..))")
    fun vipCheck() {
    }

    @Throws(Throwable::class)
    @Around("vipCheck()")
    fun aroundJointPoint(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature

        val className = signature.declaringType.simpleName

        val value = signature.method.getAnnotation(VipCheck::class.java).value
        Log.e("tag", "$className    $value")

        val startTime = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        Log.e("tag", "cost time: ${System.currentTimeMillis() - startTime}")
        return proceed
    }
}