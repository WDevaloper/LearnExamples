package com.github.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.github.RetrofitFactory
import com.github.pokemon.base.BaseViewModel
import com.github.pokemon.repository.PokeRepository
import com.github.pokemon.repository.PokemonFactory


class MainViewModel : BaseViewModel() {

    private val pokemonRepository: PokeRepository by lazy {
        PokemonFactory.makePokemonRepository(RetrofitFactory.createApi(PokeApi::class.java), App.db)
    }

    @ExperimentalPagingApi
    fun postOfData(): LiveData<PagingData<PokeResp>> = pokemonRepository.postOf().asLiveData()


    fun postOfData2() = pokemonRepository.postOf2()
            .executeFlow()
            .asLiveData()
}