package com.github.pokemon.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.pokemon.PokeApi
import com.github.pokemon.PokeRemoteMediator
import com.github.pokemon.PokeResp
import com.hi.dhl.pokemon.data.local.AppDataBase
import kotlinx.coroutines.flow.Flow

class PokeRepository(
        val config: PagingConfig,
        val api: PokeApi,
        val db: AppDataBase) {

    @ExperimentalPagingApi
    fun postOf(): Flow<PagingData<PokeResp>> {
        Log.e("tag", ">>>>>>>>>>postOf")
        return Pager(config = config,//paging 页面配置
                remoteMediator = PokeRemoteMediator(api,db)//请求远程数据
                , pagingSourceFactory = {
            Log.e("tag", ">>>>>>>>>>post")
            db.pokemonDao().getPokemon()//本地数据
        }).flow
    }
}