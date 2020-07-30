package com.github.pokemon.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.pokemon.viewmodel.base.BaseViewModel

abstract class BaseActivity : AppCompatActivity() {


    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 子类必须实现类该方法，提供具体类
     */
    abstract fun getViewModel(): BaseViewModel

    abstract fun initView(savedInstanceState: Bundle?)

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initActionViewModel()
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    private fun initActionViewModel() {
        getViewModel().let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, Observer { stateActionState ->
                when (stateActionState) {
                    is ErrorState -> stateActionState.throwable.apply { handleError(this) }
                    is LoadState -> showLoading()
                    is CompletionState -> dismissLoading()
                }
            })
        }
    }


    @MainThread
    @CallSuper
    open fun initData(savedInstanceState: Bundle?) = Unit

    /**
     * 显示开始加载数据的对话框
     */
    @MainThread
    @CallSuper
    open fun showLoading() = Unit


    /**
     * 关闭加载对话框
     */
    @MainThread
    @CallSuper
    open fun dismissLoading() = Unit

    /**
     * 处理错误
     */
    @MainThread
    @CallSuper
    open fun handleError(throwable: Throwable) = Unit

}