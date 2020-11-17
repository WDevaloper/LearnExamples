package com.github.pokemon.test


//默认生成抽象类并且构造函数是私有的
sealed class SealedType


data class Const(val number: Double) : SealedType()
data class Sum(val e1: SealedType, val e2: SealedType) : SealedType()
object NotANumber : SealedType()