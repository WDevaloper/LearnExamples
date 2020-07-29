package com.github.pokemon

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState

class RemotePagingSource(private val api: PokeApi) : PagingSource<Int, PokeResp>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeResp> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.fetchPokemonList(params.key ?: 0, 20)
            return LoadResult.Page(response.results, null, nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, PokeResp>): Int? {
        return super.getRefreshKey(state)
    }
}