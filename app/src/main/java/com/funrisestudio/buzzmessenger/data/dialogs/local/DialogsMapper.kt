package com.funrisestudio.buzzmessenger.data.dialogs.local

import com.funrisestudio.buzzmessenger.domain.dialogs.Dialog
import com.funrisestudio.buzzmessenger.domain.dialogs.MessagePreview
import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.data.room.entity.DialogRow
import javax.inject.Inject

class DialogsMapper @Inject constructor() {

    fun getDialogs(list: List<DialogRow>): List<Dialog> {
        return list.map { getDialogPreview(it) }
    }

    private fun getDialogPreview(row: DialogRow): Dialog {
        return with(row) {
            Dialog(
                contact = Contact(
                    id = contact.id,
                    name = contact.name,
                    avatar = contact.avatar
                ),
                lastMessage = MessagePreview(
                    text = lastMessage,
                    date = lastMessageDate
                ),
                unreadCount = unreadCount
            )
        }
    }

}