package com.funrisestudio.buzzmessenger

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BuzzApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}