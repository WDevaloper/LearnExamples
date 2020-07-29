package com.github.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Resp(val count: Int,
                val next: String?,
                val previous: String?,
                val results: List<PokeResp>)


@Entity
data class PokeResp(@PrimaryKey val name: String, val url: String)