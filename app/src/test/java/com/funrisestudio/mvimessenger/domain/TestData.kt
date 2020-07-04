package com.funrisestudio.mvimessenger.domain

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.domain.dialogs.MessagePreview
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.entity.Message
import java.util.*

object TestData {

    fun getMockedDialogs(): List<Dialog> {
        return listOf(
            Dialog(
                contact = Contact(
                    id = 1,
                    name = "name",
                    avatar = 0
                ),
                lastMessage = MessagePreview(
                    text = "aaaa",
                    date = Date()
                ),
                unreadCount = 1
            )
        )
    }

    fun getMockedConversation(): List<Message> {
        return listOf(
            Message(
                contactId = 1,
                text = "AAA",
                timestamp = Date(),
                isReceived = true
            )
        )
    }

}