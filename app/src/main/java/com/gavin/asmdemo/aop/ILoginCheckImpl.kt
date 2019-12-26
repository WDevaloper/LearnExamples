package com.gavin.asmdemo.aop

class ILoginCheckImpl : ILoginCheck {
    override fun doCheck(): Boolean {
        return false
    }
}