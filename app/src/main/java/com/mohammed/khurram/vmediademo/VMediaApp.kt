package com.mohammed.khurram.vmediademo

import android.app.Application
import com.mohammed.khurram.vmediademo.utils.AppConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VMediaApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

