package com.github.pokemon.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.github.pokemon.R
import com.github.pokemon.base.BaseActivity
import com.github.pokemon.viewmodel.base.BaseViewModel
import com.github.pokemon.viewmodel.SecondViewModel

class SecondActivity : BaseActivity() {

    private val mViewModel: SecondViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_jet_pack_pokemon_main
    }

    override fun getViewModel(): BaseViewModel {
        return mViewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
//        mViewModel.postOfData2().observe(this, Observer {
//            Log.e("tag", ".......................result: $it")
//        })
//
//
//        mViewModel.postOfData6().observe(this, Observer {
//            Log.e("tag", ".......................list result: $it")
//        })


        mViewModel.postOfData7().observe(this, Observer {
            Log.e("tag", ".......................list result: $it")
        })

    }


    override fun showLoading() {
        super.showLoading()
        Log.e("tag", "show loading")
    }

    override fun dismissLoading() {
        super.dismissLoading()
        Log.e("tag", "dismiss loading")
    }

    override fun handleError(throwable: Throwable) {
        super.handleError(throwable)
        Toast.makeText(this, throwable.message ?: "", Toast.LENGTH_SHORT).show()
        Log.e("tag", "handle Error")
    }
}