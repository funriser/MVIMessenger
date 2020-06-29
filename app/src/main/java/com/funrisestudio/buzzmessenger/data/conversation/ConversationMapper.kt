package com.funrisestudio.buzzmessenger.data.conversation

import com.funrisestudio.buzzmessenger.data.USER_ID
import com.funrisestudio.buzzmessenger.data.room.entity.MessageRow
import com.funrisestudio.buzzmessenger.domain.entity.Message
import javax.inject.Inject

class ConversationMapper @Inject constructor() {

    fun getMessages(list: List<MessageRow>): List<Message> {
        return list.map { getMessage(it) }
    }

    private fun getMessage(row: MessageRow): Message {
        return with(row) {
            val (contactId, isReceived) = if (receiverId == USER_ID) {
                senderId to true
            } else {
                receiverId to false
            }
            Message(
                contactId = contactId,
                text = message,
                timestamp = timestamp,
                isReceived = isReceived
            )
        }
    }

}