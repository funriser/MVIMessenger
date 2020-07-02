package com.funrisestudio.mvimessenger.data.messages

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.funrisestudio.mvimessenger.data.contacts
import com.funrisestudio.mvimessenger.data.messages
import com.funrisestudio.mvimessenger.domain.entity.Contact
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

private val messageDelay = 2000L..5000L

@AndroidEntryPoint
class MessengerService : Service() {

    private val scope = MainScope()

    @Inject lateinit var controller: MessengerServiceController

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val senderId = if (intent?.hasExtra(KEY_SENDER_ID) == true) {
            intent.getIntExtra(KEY_SENDER_ID, 1)
        } else {
            null
        }
        simulateMessage(startId, senderId)
        return START_STICKY
    }

    private fun simulateMessage(startId: Int, senderId: Int? = null) {
        val sender = if (senderId == null) {
            contacts.random()
        } else {
            contacts.first { it.id == senderId }
        }
        val message = messages.random()
        val delayMillis = Random.nextLong(messageDelay.first, messageDelay.last)
        scope.launch {
            delay(delayMillis)
            onMessageReceived(sender, message)
            stopSelf(startId)
        }
    }

    private suspend fun onMessageReceived(contact: Contact, message: String) {
        controller.onNewMessage(contact, message)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {

        private const val KEY_SENDER_ID = "sender_id"

        fun getGenerateMessagesIntent(activity: Activity, senderId: Int? = null): Intent {
            val intent = Intent(activity, MessengerService::class.java)
            if (senderId != null) {
                intent.putExtra(KEY_SENDER_ID, senderId)
            }
            return intent
        }

    }

}