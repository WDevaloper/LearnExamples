package com.github.pokemon.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.pokemon.data.entity.PokeEntity

@Database(
        entities = [PokeEntity::class],
        version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}