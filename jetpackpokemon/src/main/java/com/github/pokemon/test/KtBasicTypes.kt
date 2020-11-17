package com.github.pokemon.test

import android.text.TextUtils

/**
 * 基本类型
 */
object KtBasicTypes {
    @JvmStatic
    fun main(args: Array<String>) {
        val (nameV, ageV) = Person("kotlin", 1,Man("",0))
        println("$nameV   $ageV")
        val hashMap = HashMap<Int, Int>()
        hashMap.put(0,0)
    }
}
