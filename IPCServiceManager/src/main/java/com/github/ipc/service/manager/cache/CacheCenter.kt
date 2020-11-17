package com.github.ipc.service.manager.cache

import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

object CacheCenter {

    // key = class_name  value = Class<*>
    private val mClassMap = ConcurrentHashMap<String, Class<*>>()

    //  key = class_name  value = ConcurrentHashMap<method_name, Method>
    // 一个类中有多个方法
    private val mAllMethodMap = ConcurrentHashMap<String, ConcurrentHashMap<String, Method>>()


    @JvmStatic
    fun register(clazz: Class<*>) {
        registerClass(clazz)
        registerMethod(clazz)
    }

    @JvmStatic
    private fun registerClass(clazz: Class<*>) {
        mClassMap[clazz.name] = clazz
    }

    private fun registerMethod(clazz: Class<*>) {
        val methods = clazz.declaredMethods
        for (method in methods) {
            var methodMap = mAllMethodMap[clazz.name]
            if (methodMap == null) {
                methodMap = ConcurrentHashMap()
                mAllMethodMap[clazz.name] = methodMap
            }
            methodMap[getMethodParams(method)] = method
        }
    }

    private fun getMethodParams(method: Method): String {
        val result = StringBuffer(method.name)
        val parameterTypes = method.parameterTypes
        if (parameterTypes.isEmpty()) return result.toString()
        for (parameterType in parameterTypes) {
            result.append("-").append(parameterType.name)
        }
        return result.toString()
    }
}