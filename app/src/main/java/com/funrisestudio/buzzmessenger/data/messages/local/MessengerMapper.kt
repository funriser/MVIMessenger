package com.funrisestudio.buzzmessenger.data.messages.local

import com.funrisestudio.buzzmessenger.domain.Sender
import com.funrisestudio.buzzmessenger.data.room.entity.MessageRow
import com.funrisestudio.buzzmessenger.data.room.entity.SenderRow
import java.util.*
import javax.inject.Inject

class MessengerMapper @Inject constructor() {

    fun toSenderRow(sender: Sender): SenderRow {
        return with(sender) {
            SenderRow(
                id = id,
                name = name,
                avatar = avatar
            )
        }
    }

    fun toMessageRow(sender: Sender, message: String): MessageRow {
        return MessageRow(
            senderId = sender.id,
            message = message,
            timestamp = Date()
        )
    }

}