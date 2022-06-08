package com.mohammed.khurram.vmediademo.models

import android.os.Parcelable
import com.mohammed.khurram.vmediademo.models.Stat
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stats(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
) : Parcelable