package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.data.contacts
import com.funrisestudio.mvimessenger.data.messages
import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.domain.dialogs.MessagePreview
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