package com.github.pokemon.data.api

import com.github.pokemon.data.protocol.PokeResp
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun fetchPokemonList(
            @Query("limit") limit: Int = 20,
            @Query("offset") offset: Int = 0
    ): PokeResp


    @GET("pokemon")
    suspend fun fetchPokemonList2(
            @Query("limit") limit: Int = 20,
            @Query("offset") offset: Int = 0
    ): PokeResp
}