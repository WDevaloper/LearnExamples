package com.github.pokemon.data.protocol

import com.github.pokemon.data.entity.PokeEntity

data class PokeResp(val count: Int,
                    val next: String?,
                    val previous: String?,
                    val results: List<PokeEntity>)
