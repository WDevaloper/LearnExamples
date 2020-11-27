package com.github.ipc.service.manager.core

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.text.TextUtils
import com.github.ipc.service.manager.IBinderlInterface
import com.github.ipc.service.manager.IpcServiceManager
import com.github.ipc.service.manager.annotation.ClassId
import com.github.ipc.service.manager.cache.CacheCenter
import com.google.gson.Gson
import java.lang.reflect.Method


object RealBinder {
    //真正做进程通信
    private lateinit var mBinderInterface: IBinderlInterface

    private lateinit var sContext: Context

    private val gson = Gson()

    fun open(context: Context) {
        open(context, null)
    }

    fun init(context: Context) {
        sContext = context.applicationContext
    }


    fun open(context: Context, packageName: String?) {
        init(context)
        bind(context.applicationContext, packageName, IpcServiceManager::class.java)
    }

    // 注册服务方法  目的就是缓存服务提供的方法
    fun register(service: Class<*>) {
        CacheCenter.register(service)
    }

    private fun bind(context: Context, packageName: String?, service: Class<IpcServiceManager>) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent().apply {
                component = ComponentName(packageName ?: "", service.name)
                action = service.name
            }
        } else {
            Intent(context, service)
        }.let { intent ->
            context.bindService(
                    intent,
                    IpcServiceManagerConnection(),
                    Service.BIND_AUTO_CREATE
            )
        }
    }

    fun <T> getInstance(clazz: Class<T>, vararg parameters: Any): T {
        //获取服务不需要 调用方法
        sendRequest(clazz, null, parameters, IpcServiceManager.TYPE_GET)
    }


    private fun <T> sendRequest(clazz: Class<T>, method: Method?, parameters: Array<out Any>?, type: Int) {
        // 发送通知   json
        var param: ArrayList<Parameter>? = null
        if (parameters != null && parameters.isNotEmpty()) {
            param = ArrayList(parameters.size)
            // val p:Int ---->   p.getClass().getName() 拿到的是 类型名    p则是拿到则是值
            parameters.forEach { Parameter(it.javaClass.name, gson.toJson(it)).run(param::add) }
        }

        val className: String = clazz.getAnnotation(ClassId::class.java)?.value ?: ""
        val methodName: String = method?.name ?: "getInstance"
        val requestBeanJson = RequestBean(className, methodName, param, type).run(gson::toJson)
        try {
            val response = mBinderInterface.request(requestBeanJson)
        } catch (ex: RemoteException) {
            ex.printStackTrace()
        }
    }


    //进程通信
    private class IpcServiceManagerConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) = Unit
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 可通过 mBinderInterface 进行进程通信
            mBinderInterface = IBinderlInterface.Stub.asInterface(service)
        }
    }

}