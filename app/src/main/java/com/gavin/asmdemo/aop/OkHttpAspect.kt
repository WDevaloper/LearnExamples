package com.gavin.asmdemo.aop

import android.util.Log
import okio.ByteString
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
class OkHttpAspect {

    @Pointcut("execution( * okhttp3.Cache.key(..))")
    fun testStatic() {
    }

    @Throws(Throwable::class)
    @Around("testStatic()")
    fun aroundJointPoint(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature

        val className = signature.declaringType.simpleName


        val args = joinPoint.args


        //切点的参数，比如方法参数，这样能拿到参数的值
        args?.forEach {
            Log.e("tag", "args>>>>>>>$it")
        }


        Log.e("tag", "${signature.method.name}  ===== $className")



        signature.method.parameterTypes?.forEach {
            Log.e("tag", "${it.simpleName}")
        }


        val startTime = System.currentTimeMillis()
        val proceed = joinPoint.proceed()
        Log.e("tag", "cost time: ${System.currentTimeMillis() - startTime}")
        return proceed
    }
}