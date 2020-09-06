package com.github.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.github.pokemon.data.RetrofitFactory
import com.github.pokemon.base.App
import com.github.pokemon.data.api.PokeApi
import com.github.pokemon.data.entity.PokeEntity
import com.github.pokemon.data.repository.PokeRepository
import com.github.pokemon.data.repository.PokemonFactory
import com.github.pokemon.viewmodel.base.BaseViewModel


class MainViewModel : BaseViewModel() {

    private val pokemonRepository: PokeRepository by lazy {
        PokemonFactory.makePokemonRepository(RetrofitFactory.createApi(PokeApi::class.java), App.db)
    }

    @ExperimentalPagingApi
    fun postOfData(): LiveData<PagingData<PokeEntity>> = pokemonRepository.postOf().asLiveData()
}