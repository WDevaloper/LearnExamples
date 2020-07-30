package com.github.pokemon.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pokemon.base.CompletionState
import com.github.pokemon.base.ErrorState
import com.github.pokemon.base.LoadState
import com.github.pokemon.base.StateActionEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

open class BaseViewModel : ViewModel() {
    //通用事件模型驱动(如：显示对话框、取消对话框、错误提示)
    internal val mStateLiveData by lazy { MutableLiveData<StateActionEvent>() }

    fun <T> Flow<T>.executeFlow(): Flow<T> {
        return this.onStart {
            mStateLiveData.value = LoadState
        }.catch { throwable ->
            throwable.printStackTrace()
            // mStateLiveData.postValue(ErrorState(throwable)) 内部使用了Handler并且方法内mPendingData=value造成覆盖前一个发送的值
            mStateLiveData.value = ErrorState(throwable)
        }.onCompletion {
            mStateLiveData.value = CompletionState
        }
    }
}