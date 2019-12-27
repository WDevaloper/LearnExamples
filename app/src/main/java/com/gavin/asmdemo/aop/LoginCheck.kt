package com.gavin.asmdemo.aop

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class LoginCheck(val value: KClass<out ILoginCheck>, val arouterPath: String = "")