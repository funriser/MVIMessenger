package com.funrisestudio.buzzmessenger

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.ui.graphics.toArgb
import com.funrisestudio.buzzmessenger.Notifier.Companion.CHANNEL_DESCRIPTION
import com.funrisestudio.buzzmessenger.Notifier.Companion.CHANNEL_ID
import com.funrisestudio.buzzmessenger.Notifier.Companion.CHANNEL_NAME
import com.funrisestudio.buzzmessenger.Notifier.Companion.ID
import com.funrisestudio.buzzmessenger.ui.colorPrimary
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

interface Notifier {

    fun sendMessageNotification(sender: Sender, message: String)

    companion object {
        const val ID = 42
        const val CHANNEL_ID = "buzz_channel"
        const val CHANNEL_NAME = "Buzz Channel"
        const val CHANNEL_DESCRIPTION = "Buzz Channel Description"
    }
}

class NotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
): Notifier {

    private val notificationManager by lazy {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .also {
                createNotificationChannel(it)
            }
    }

    override fun sendMessageNotification(sender: Sender, message: String) {
        val intent = createMessengerBubbleIntent()
        val avatarIcon = Icon.createWithResource(context, sender.avatar)

        val bubbleData = Notification.BubbleMetadata.Builder(intent, avatarIcon)
                .setDesiredHeight(600)
                .build()

        val user = Person.Builder()
            .setName(sender.name)
            .setIcon(avatarIcon)
            .setImportant(true)
            .build()

        val style = Notification.MessagingStyle(user)
                .addMessage(message, Date().time, user)

        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_msg)
            .setColor(colorPrimary.toArgb())
            .setBubbleMetadata(bubbleData)
            .addPerson(user)
            .setStyle(style)
            .build()

        notificationManager.notify(ID, notification)
    }

    private fun createMessengerBubbleIntent(): PendingIntent {
        val target = Intent(context, MessengerActivity::class.java)
        return PendingIntent.getActivity(context, 0, target, 0 /* flags */)
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
                setAllowBubbles(true)
            }
            // Register the channel with the system
            nm.createNotificationChannel(channel)
        }
    }

}

class StubNotifier: Notifier {
    override fun sendMessageNotification(sender: Sender, message: String) {
    }
}