package com.funrisestudio.mvimessenger.domain

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.domain.dialogs.MessagePreview
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.entity.Message
import com.funrisestudio.mvimessenger.ui.dialogs.DialogViewData
import java.util.*

object TestData {

    fun getMockedDialogs(): List<Dialog> {
        return listOf(getMockedDialog())
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

    fun getDialogViewData(): DialogViewData {
        return DialogViewData(
            dialog = getMockedDialog(),
            formattedDate = "01.01.2020"
        )
    }

    fun getMockedContact(): Contact {
        return Contact(
            id = 1,
            name = "name",
            avatar = 0
        )
    }

    private fun getMockedDialog(): Dialog {
        return Dialog(
            contact = getMockedContact(),
            lastMessage = MessagePreview(
                text = "aaaa",
                date = Date()
            ),
            unreadCount = 1
        )
    }

}