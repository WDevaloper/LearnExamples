package com.github.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pokemon.base.BaseActivity
import com.github.pokemon.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_jet_pack_pokemon_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest


/**
 * LiveData 最好只在View和ViewModel中使用，因为LiveData是具有生命周期感知的组件。
 *
 * 假如你的项目架构是 Repository模式，那么非常简单了
 */


class JetPackPokemonMainActivity : BaseActivity() {

    private val mViewModel: MainViewModel by viewModels()

    private val mPomemonAdapter by lazy { PokeAdapter() }


    override fun getLayoutId(): Int {
        return R.layout.activity_jet_pack_pokemon_main
    }

    override fun initView() {
        mRv.layoutManager = LinearLayoutManager(this)
        mRv.adapter = mPomemonAdapter
//
//        mViewModel.postOfData().observe(this, Observer {
//
//            Log.e("tag", ">>>>>>>>>>>>>>>>>>>>")
//            mPomemonAdapter.submitData(lifecycle, it)
//        })

        mViewModel.postOfData2().observe(this, Observer {
            Log.e("tag", ">>>>>>>>>>>>>>>>>>>>$it")
        })

//        LoadState.Loading
        lifecycleScope.launchWhenCreated {
            mPomemonAdapter.loadStateFlow.collectLatest { state ->
                Log.e("tag", ">>>>>>>>>>>>>>>>>>>>${state}")
            }
        }
    }

    override fun getViewModel(): BaseViewModel {
        return mViewModel
    }

    class PokeAdapter : PagingDataAdapter<PokeResp, PokeViewHolder>(POST_COMPARATOR) {
        companion object {
            val POST_COMPARATOR = object : DiffUtil.ItemCallback<PokeResp>() {
                override fun areContentsTheSame(oldItem: PokeResp, newItem: PokeResp): Boolean =
                        oldItem == newItem

                override fun areItemsTheSame(oldItem: PokeResp, newItem: PokeResp): Boolean =
                        oldItem.url == newItem.url
            }
        }


        override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
            holder.bind(getItem(position)!!)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
            return PokeViewHolder(TextView(parent.context))
        }
    }


    class PokeViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(data: PokeResp) {
            textView.text = data.name
        }
    }
}
