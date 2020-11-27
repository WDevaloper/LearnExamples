package com.github.ipc.service.manager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel


// IPC服务管理类
class IpcServiceManager : Service() {

    companion object {
        const val TYPE_GET: Int = 1
        const val TYPE_INVOKE: Int = 2
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }


    //在主进程
    private val mBinder: IBinderlInterface.Stub = object : IBinderlInterface.Stub() {
        override fun request(request: String): String? {

            return null
        }
    }
}