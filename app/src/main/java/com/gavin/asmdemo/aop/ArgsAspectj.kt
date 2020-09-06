package com.gavin.asmdemo.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut


@Aspect
class ArgsAspectj {

    @Pointcut("execution(* com.gavin.asmdemo.aop.AOPActivity.testArgs(String,String) && args(args1,args2)")
    fun getArgs() {
    }


    @Around("getArgs(args1,args2)")
    fun argsAspect(joint: ProceedingJoinPoint, args1: String, args2: String) {
    }
}