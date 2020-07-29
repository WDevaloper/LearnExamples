package com.github.pokemon.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentLayout()
    }


    open fun setContentLayout() {
        setContentView(getLayoutId())
        initViewModelAction()
        initView()
        initData()
    }

    private fun initViewModelAction() {
        getViewModel().let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, Observer { stateActionState ->
                Log.e("tag", "$stateActionState")
                when (stateActionState) {
                    is ErrorState -> stateActionState.throwable.apply { handleError(this) }
                    is LoadState -> showLoading()
                    is CompletionState -> dismissLoading()
                }
            })
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun getViewModel(): BaseViewModel


    open fun initData() {}

    open fun showLoading() {
        Log.e("tag", "showLoading")
    }

    open fun dismissLoading() {
        Log.e("tag", "dismissLoading")
    }

    open fun handleError(throwable: Throwable) {
        Log.e("tag", "handleError")
    }

}