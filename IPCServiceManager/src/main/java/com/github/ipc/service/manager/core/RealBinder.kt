package com.github.ipc.service.manager.core

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.text.TextUtils
import com.github.ipc.service.manager.IBinderlInterface
import com.github.ipc.service.manager.IpcServiceManager
import com.github.ipc.service.manager.cache.CacheCenter


object RealBinder {
    //真正做进程通信
    private lateinit var mBinderInterface: IBinderlInterface

    private lateinit var sContext: Context

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

    //进程通信
    private class IpcServiceManagerConnection : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) = Unit
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 可通过 mBinderInterface 进行进程通信
            mBinderInterface = IBinderlInterface.Stub.asInterface(service)
        }
    }

}