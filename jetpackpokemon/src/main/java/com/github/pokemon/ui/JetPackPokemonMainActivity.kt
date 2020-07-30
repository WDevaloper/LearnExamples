package com.github.pokemon.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pokemon.R
import com.github.pokemon.base.BaseActivity
import com.github.pokemon.viewmodel.base.BaseViewModel
import com.github.pokemon.data.entity.PokeEntity
import com.github.pokemon.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_jet_pack_pokemon_main.*
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

    override fun initView(savedInstanceState: Bundle?) {
        mRv.layoutManager = LinearLayoutManager(this)
        mRv.adapter = mPomemonAdapter
    }


    @ExperimentalPagingApi

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.postOfData().observe(this, Observer {
            Log.e("tag", ">>>>>>>>>>>>>>>>>>>>")
            mPomemonAdapter.submitData(lifecycle, it)
        })


        // LoadState.Loading
        lifecycleScope.launchWhenCreated {
            mPomemonAdapter.loadStateFlow.collectLatest { state ->
                Log.e("tag", ">>>>>>>>>>>>>>>>>>>>${state}")
            }
        }
    }

    override fun getViewModel(): BaseViewModel {
        return mViewModel
    }

    class PokeAdapter : PagingDataAdapter<PokeEntity, PokeViewHolder>(POST_COMPARATOR) {
        companion object {
            val POST_COMPARATOR = object : DiffUtil.ItemCallback<PokeEntity>() {
                override fun areContentsTheSame(oldItem: PokeEntity, newItem: PokeEntity): Boolean =
                        oldItem == newItem

                override fun areItemsTheSame(oldItem: PokeEntity, newItem: PokeEntity): Boolean =
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
        fun bind(data: PokeEntity) {
            textView.text = data.name
        }
    }

    fun jumpSecondActivity(view: View) {
        Intent(this, SecondActivity::class.java).run { startActivity(this) }
    }
}
