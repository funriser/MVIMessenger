package com.funrisestudio.buzzmessenger

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.funrisestudio.buzzmessenger.data.MessengerServiceController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MessengerService : Service() {

    private val scope = MainScope()

    @Inject lateinit var controller: MessengerServiceController

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        simulateMessage()
        return START_STICKY
    }

    private fun simulateMessage() {
        val sender = Sender.macFly()
        val message = "It's time"
        val delayMillis = Random.nextLong(5000, 10000)
        scope.launch {
            delay(delayMillis)
            onMessageReceived(sender, message)
            stopSelf()
        }
    }

    private suspend fun onMessageReceived(sender: Sender, message: String) {
        controller.onNewMessage(sender, message)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}