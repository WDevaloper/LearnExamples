package com.github.aop

import android.util.Log
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.lang.NullPointerException

@Aspect
object JoinPointStudyAspectj {

    //[!] [@Annotation] [public,protected,private] [static] [final] 返回值类型 [类名.]方法名(参数类型列表) [throws 异常类型]
    @Pointcut("execution(void com.github.aop.JoinPointStudyTestClass.test(..))")
    fun methodExecution() = Unit

    @Around("methodExecution()")
    @Throws(Throwable::class)
    fun methodExecutionAspect(joinPoint: ProceedingJoinPoint) {
        Log.e("tag", "methodExecution")
        joinPoint.proceed()
    }

    //[!] [@Annotation] [public,protected,private] [final] [类名.]new(参数类型列表) [throws 异常类型]
    //JoinPointStudyTestClass 类及其子类的构造函数被调用时没植入代码，在被调用处织入代码
    @Pointcut("execution(com.github.aop.JoinPointStudyTestClass+.new(..))")
    fun constructorExecution() = Unit


    @Around("constructorExecution()")
    @Throws(Throwable::class)
    fun constructorCallAspect(joinPoint: ProceedingJoinPoint) {
        Log.e("tag", "constructorCallAspect")
        joinPoint.proceed()
    }

    // [!] [@Annotation] [public,protected,private] [static] [final] 属性类型 [类名.]属性名
    @Pointcut("set(String com.github.aop.JoinPointStudyTestClass+.name)")
    fun fieldExecutionRead() = Unit


    @Around("fieldExecutionRead()")
    @Throws(Throwable::class)
    fun fieldCallAspectRead(joinPoint: ProceedingJoinPoint) {
        Log.e("tag", "fieldCallAspectRead")
        joinPoint.proceed()
    }


    // [!] [@Annotation] [public,protected,private] [static] [final] 属性类型 [类名.]属性名
    @Pointcut("staticinitialization(com.github.aop.JoinPointStudyTestClass)")
    fun staticInitialization() = Unit

    @Around("staticInitialization()")
    @Throws(Throwable::class)
    fun staticInitializationAspect(joinPoint: ProceedingJoinPoint) {
        Log.e("tag", "staticInitialization")
        joinPoint.proceed()
    }
}

