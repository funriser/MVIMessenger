package com.funrisestudio.buzzmessenger.ui.main

import com.funrisestudio.buzzmessenger.data.contacts
import com.funrisestudio.buzzmessenger.data.messages
import com.funrisestudio.buzzmessenger.domain.Dialog
import com.funrisestudio.buzzmessenger.domain.MessagePreview
import java.util.*

data class DialogViewData(
    val dialog: Dialog,
    val formattedDate: String
)

fun getFakeDialogViewData(): List<DialogViewData> {
    return listOf(
        DialogViewData(
            Dialog(
                contact = contacts.random(),
                lastMessage = MessagePreview(
                    text = messages.random(),
                    date = Date()
                ),
                unreadCount = 1
            ),
            formattedDate = "15:02"
        ),
        DialogViewData(
            Dialog(
                contact = contacts.random(),
                lastMessage = MessagePreview(
                    text = messages.random(),
                    date = Date()
                ),
                unreadCount = 1
            ),
            formattedDate = "15:02"
        ),
        DialogViewData(
            Dialog(
                contact = contacts.random(),
                lastMessage = MessagePreview(
                    text = messages.random(),
                    date = Date()
                ),
                unreadCount = 1
            ),
            formattedDate = "15:02"
        )
    )
}