package com.github.ipc.service.manager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcel


// IPC服务管理类
class IpcServiceManager : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }


    private val mBinder: IBinderlInterface.Stub = object : IBinderlInterface.Stub() {
        override fun request(request: String): String? {
            return null
        }
    }
}