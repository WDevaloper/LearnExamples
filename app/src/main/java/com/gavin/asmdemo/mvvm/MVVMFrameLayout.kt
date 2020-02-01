package com.gavin.asmdemo.mvvm

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.gavin.asmdemo.R
import com.gavin.asmdemo.databinding.MvvmFrameLayoutBind

class MVVMFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val layoutBind: MvvmFrameLayoutBind =
            DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.mvvm_layout, this, true)
    fun bindData(user: User) {
        layoutBind.user = user
    }
}