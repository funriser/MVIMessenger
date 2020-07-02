package com.funrisestudio.buzzmessenger

import android.app.*
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.ui.graphics.toArgb
import com.funrisestudio.buzzmessenger.Notifier.Companion.CHANNEL_DESCRIPTION
import com.funrisestudio.buzzmessenger.Notifier.Companion.CHANNEL_ID
import com.funrisestudio.buzzmessenger.Notifier.Companion.CHANNEL_NAME
import com.funrisestudio.buzzmessenger.Notifier.Companion.ID
import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.ui.colorPrimary
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

interface Notifier {

    fun sendMessageNotification(contact: Contact, message: String)

    companion object {
        const val ID = 42
        const val CHANNEL_ID = "buzz_channel"
        const val CHANNEL_NAME = "Buzz Channel"
        const val CHANNEL_DESCRIPTION = "Buzz Channel Description"
    }
}

class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
): Notifier, LifecycleObserver {

    private var isInBackground = false

    private val notificationManager by lazy {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .also {
                createNotificationChannel(it)
            }
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun sendMessageNotification(contact: Contact, message: String) {
        val intent = createMessengerIntent(contact)
        val avatarIcon = Icon.createWithResource(context, contact.avatar)

        val user = Person.Builder()
            .setName(contact.name)
            .setIcon(avatarIcon)
            .setImportant(true)
            .build()

        val style = Notification.MessagingStyle(user)
                .addMessage(message, Date().time, user)

        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_msg)
            .setColor(colorPrimary.toArgb())
            .addPerson(user)
            .setStyle(style)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()

        dispatchNotification(notification)
    }

    private fun dispatchNotification(notification: Notification) {
        if (isInBackground) {
            notificationManager.notify(ID, notification)
        }
    }

    private fun createMessengerIntent(contact: Contact): PendingIntent {
        val target = ConversationActivity.getIntent(context, contact)
        return PendingIntent.getActivity(context, 0, target,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createNotificationChannel(nm: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                importance
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            // Register the channel with the system
            nm.createNotificationChannel(channel)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        isInBackground = false
        notificationManager.cancelAll()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        isInBackground = true
    }

}

class StubNotifier: Notifier {
    override fun sendMessageNotification(contact: Contact, message: String) {
    }
}