package com.github.pokemon.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Man(val name: String, val age: Int) : Parcelable