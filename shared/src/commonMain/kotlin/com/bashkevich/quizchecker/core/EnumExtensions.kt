package com.bashkevich.quizchecker.core

fun<T : Enum<T>> Enum<T>.convertFromEnum() = this.name.replace("_"," ").lowercase()