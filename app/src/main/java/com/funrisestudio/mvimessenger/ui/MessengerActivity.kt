package com.funrisestudio.mvimessenger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.data.messages.MessengerService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessengerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        initMessaging(savedInstanceState == null)
    }

    private fun initMessaging(isFirstStarted: Boolean) {
        if (isFirstStarted) {
            startService(MessengerService.getGenerateMessagesIntent(this))
        }
    }

}