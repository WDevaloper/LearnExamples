package com.github.pokemon

import kotlinx.android.parcel.Parcelize
import org.junit.Test

import org.junit.Assert.*


class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testKotlin() {
        val nullableList: List<Int?> = listOf(1, 2, null, 4)
        val intList: List<Int> = nullableList.filterNotNull()
        println(intList)
    }
}
