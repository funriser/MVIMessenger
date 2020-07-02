package com.funrisestudio.mvimessenger

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MessengerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}