package com.github.pokemon.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//数据类 默认生成 component1 component2 componentXXX
@Parcelize
data class Person(val name: String?, val age: Int,val main:Man) : Parcelable


