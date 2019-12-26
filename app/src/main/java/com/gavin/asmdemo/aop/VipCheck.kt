package com.gavin.asmdemo.aop


@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class VipCheck(val value: String)