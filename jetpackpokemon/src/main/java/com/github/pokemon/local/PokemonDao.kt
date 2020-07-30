package com.github.pokemon.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.pokemon.data.entity.PokeEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM PokeEntity")
    fun getPokemon(): PagingSource<Int, PokeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonList: List<PokeEntity>)

}