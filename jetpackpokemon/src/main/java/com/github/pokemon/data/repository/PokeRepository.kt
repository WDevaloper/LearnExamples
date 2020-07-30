package com.github.pokemon.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.pokemon.data.api.PokeApi
import com.github.pokemon.data.source.PokeRemoteMediator
import com.github.pokemon.data.entity.PokeEntity
import com.github.pokemon.data.protocol.PokeResp
import com.github.pokemon.local.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class PokeRepository(
        private val config: PagingConfig,
        private val api: PokeApi,
        private val db: AppDataBase
) {

    @ExperimentalPagingApi
    fun postOf(): Flow<PagingData<PokeEntity>> {
        return Pager(config = config,//paging 页面配置
                remoteMediator = PokeRemoteMediator(api, db)//请求远程数据
                , pagingSourceFactory = {
            db.pokemonDao().getPokemon()//本地数据
        }).flow
    }

    //记住一定发射出去
    @ExperimentalCoroutinesApi
    fun postOf2(): Flow<PokeResp> = flow { emit(api.fetchPokemonList()) }.flowOn(Dispatchers.IO)
}