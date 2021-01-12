package com.gavin.asmdemo.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut


@Aspect
object ArgsAspectj {

    const val TAG = "ArgsAspectj"

    @Pointcut("execution(* com.gavin.asmdemo.aop.AOPActivity.testArgs(String,String) && args(args1,args2)")
    fun getArgs(args1: String, args2: String) {
    }


    @Around("getArgs(args1,args2)")
    fun argsAspect(joint: ProceedingJoinPoint, args1: String, args2: String) {
        Log.e(TAG, "argsAspect: args1=$args1 , args2=$args2")
    }
}