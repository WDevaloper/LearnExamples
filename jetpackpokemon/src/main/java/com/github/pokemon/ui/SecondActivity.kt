package com.github.pokemon.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.lifecycleScope
import com.github.pokemon.R
import com.github.pokemon.base.BaseActivity
import com.github.pokemon.base.MyLifecycle
import com.github.pokemon.event.EventType
import com.github.pokemon.event.LiveDataBus
import com.github.pokemon.viewmodel.base.BaseViewModel
import com.github.pokemon.viewmodel.SecondViewModel
import dagger.hilt.android.AndroidEntryPoint
import dalvik.system.BaseDexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

@AndroidEntryPoint
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


//        mViewModel.postOfData7().observe(this, Observer {
//            Log.e("tag", ".......................list result: $it")
//        })

//        ReportFragment.injectIfNeededIn(this)


        mViewModel.postOf8().observe(this, Observer {
            Log.e("tag", "result>>>> $it")
        })


        lifecycleScope.launchWhenCreated {
            Log.e("tag", "launchWhenCreated>>>${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                val singleValue = flow {
                    emit(8)
                } // will be executed on IO if context wasn't specified before
                        .map { Log.e("tag", "map>>>${Thread.currentThread().name}") } // Will be executed in IO
                        .flowOn(Dispatchers.IO)
                        .retry(3000) { true }
                        .catch { }
                        .buffer()
                        .filter {
                            Log.e("tag", "filter>>>${Thread.currentThread().name}")
                            true
                        } // Will be executed in Default
                        .flowOn(Dispatchers.Default)
                        .single() // Will be executed in the Main
            }
        }


        // 为什么加Type？因为我们需要避免LiveDataBus.with<EventType>("name")

        // 使用 observe 函数进行订阅的话，只会在宿主处于前台的状态才会进行回调，

        // 也就是如果 activity 被挂在后台了，那么消息会等到 activity 返回前台
        // 时再进行回调，这就是 LiveData 的生命周期感知能力带来的好处。
        LiveDataBus.with("EventType", EventType::class.java)
                .observe(this, Observer {
                    Log.e("tag", "SecondActivity ${it.name}")
                })

        LiveDataBus.with("EventType", EventType::class.java).observeForever {

        }


    }

    fun sendMessage(view: View) {
        LiveDataBus.with("EventType", EventType::class.java).value = EventType("SecondActivity")
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