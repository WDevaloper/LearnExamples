package com.github.pokemon.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.pokemon.data.entity.PokeEntity
import com.github.pokemon.data.api.PokeApi

class RemotePagingSource(private val api: PokeApi) : PagingSource<Int, PokeEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeEntity> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.fetchPokemonList(params.key ?: 0, 20)
            return LoadResult.Page(response.results, null, nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, PokeEntity>): Int? {
        return super.getRefreshKey(state)
    }
}