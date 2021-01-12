package com.github.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.*

@Aspect
object ArgsAspectj {

    @Pointcut("execution(* com.github.aop.AopArgsTest.argsMethod1(String))")
    fun argsMethodPointCut() {
    }


    @Around("argsMethodPointCut()")
    @Throws(Throwable::class)
    fun argsAspect(joinPoint: ProceedingJoinPoint) {
        val signature = joinPoint.getSignature()
        Log.e(Constants.TAG, "argsAspect: ")
        try {
            joinPoint.proceed()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    @AfterThrowing(value = "argsMethodPointCut()", throwing = "ex")
    fun throwingMethod(joinPoint: ProceedingJoinPoint, ex: Exception) {
        val methodName: String = joinPoint.getSignature().getName()
        val args: List<Any> = Arrays.asList(joinPoint.getArgs())
        Log.e(Constants.TAG, "连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);
    }
}