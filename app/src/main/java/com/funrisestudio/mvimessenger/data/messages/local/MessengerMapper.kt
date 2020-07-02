package com.funrisestudio.mvimessenger.data.messages.local

import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.data.room.entity.MessageRow
import com.funrisestudio.mvimessenger.data.room.entity.ContactRow
import java.util.*
import javax.inject.Inject

class MessengerMapper @Inject constructor() {

    fun toContactRow(contact: Contact): ContactRow {
        return with(contact) {
            ContactRow(
                id = id,
                name = name,
                avatar = avatar
            )
        }
    }

    fun toMessageRow(contact: Contact, message: String): MessageRow {
        return MessageRow(
            senderId = contact.id,
            message = message,
            timestamp = Date(),
            isRead = false
        )
    }

}