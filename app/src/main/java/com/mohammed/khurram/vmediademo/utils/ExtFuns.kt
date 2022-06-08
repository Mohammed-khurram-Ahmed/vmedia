package com.mohammed.khurram.vmediademo.utils

fun String.getImageUrl(): String {
    val id = this.substringAfter("pokemon").replace("/", "").toInt()
    return "${AppConstants.IMAGE_BASE_URL}${id}.png"
}