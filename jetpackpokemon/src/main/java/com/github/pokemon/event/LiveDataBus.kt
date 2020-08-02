package com.github.pokemon.event

import androidx.lifecycle.MutableLiveData

object LiveDataBus {
    private val mLiveDataBus: MutableMap<String, MutableLiveData<Any>>
            by lazy { HashMap<String, MutableLiveData<Any>>() }


    @Synchronized
    fun <T> with(key: String, eventType: Class<T>): MutableLiveData<T> {
        if (!mLiveDataBus.containsKey(key)) {
            mLiveDataBus[key] = MutableLiveData()
        }
        return mLiveDataBus[key] as MutableLiveData<T>
    }
}