package com.github.binderlearn.ipc


//每一个需要提供服务的 都需要实现接口
class IPCServiceImpl : IPCService {
    override fun getUser(): String {
        return "hahahah"
    }

    override fun getUser(name: String): UserInfo {
        return UserInfo("1243", "fy")
    }
}