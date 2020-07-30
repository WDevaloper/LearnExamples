package com.github.pokemon.data.source

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.github.pokemon.data.entity.PokeEntity
import com.github.pokemon.data.api.PokeApi
import com.github.pokemon.local.AppDataBase
import kotlinx.coroutines.delay


/**
 * 负责加载网路数据，并且把数据保存本地
 *
 */
@ExperimentalPagingApi
class PokeRemoteMediator(
        val api: PokeApi,
        val db: AppDataBase
) : RemoteMediator<Int, PokeEntity>() {

    //挂起函数
    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokeEntity>): MediatorResult {
        val pageKey = when (loadType) {
            // 首次访问 或者调用 PagingDataAdapter.refresh()
            LoadType.REFRESH -> {
                Log.e("tag", "PokeRemoteMediator  LoadType.REFRESH")
                null
            }
            // 在当前加载的数据集的开头加载数据时
            LoadType.PREPEND -> {
                Log.e("tag", "PokeRemoteMediator  LoadType.PREPEND")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            // 在当前数据集末尾添加数据
            LoadType.APPEND -> {
                Log.e("tag", "PokeRemoteMediator  LoadType.APPEND")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        Log.e("tag", "PokeRemoteMediator suspend")
        db.withTransaction {
            val pokemonDao = db.pokemonDao()

            pokemonDao.insertPokemon(
                    listOf(PokeEntity("asfdsfsd", "12321"))
            )
        }
        delay(5000)
        Log.e("tag", "PokeRemoteMediator finish")
        return MediatorResult.Success(endOfPaginationReached = false)
    }
}