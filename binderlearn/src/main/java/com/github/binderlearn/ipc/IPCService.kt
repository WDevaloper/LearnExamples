package com.github.binderlearn.ipc

import com.github.ipc.service.manager.annotation.ClassId


@ClassId("com.github.binderlearn.ipc.IPCServiceImpl")
interface IPCService {
    fun getUser(): String
    fun getUser(name: String): UserInfo
}