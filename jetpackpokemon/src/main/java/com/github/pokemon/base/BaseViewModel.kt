package com.github.pokemon.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

open class BaseViewModel : ViewModel() {
    //通用事件模型驱动(如：显示对话框、取消对话框、错误提示)
    val mStateLiveData: MutableLiveData<StateActionEvent> = MutableLiveData()

    fun <T> Flow<T>.executeFlow(): Flow<T> {
        return this.onStart {
            mStateLiveData.postValue(LoadState)
        }.catch { throwable ->
            mStateLiveData.postValue(ErrorState(throwable))
        }.onCompletion {
            mStateLiveData.postValue(CompletionState)
        }
    }

}