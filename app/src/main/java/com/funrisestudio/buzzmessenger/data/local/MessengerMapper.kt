package com.funrisestudio.buzzmessenger.data.local

import com.funrisestudio.buzzmessenger.Sender
import com.funrisestudio.buzzmessenger.data.room.entity.MessageRow
import com.funrisestudio.buzzmessenger.data.room.entity.SenderRow
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
            message = message
        )
    }

}