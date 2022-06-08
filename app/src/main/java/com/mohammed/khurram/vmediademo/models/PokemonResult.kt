package com.mohammed.khurram.vmediademo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResult(
    val name: String,
    val url: String,
    var actualUrl: String?
) : Parcelable


