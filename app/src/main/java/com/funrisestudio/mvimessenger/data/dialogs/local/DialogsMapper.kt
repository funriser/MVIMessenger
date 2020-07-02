package com.funrisestudio.mvimessenger.data.dialogs.local

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.domain.dialogs.MessagePreview
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.data.room.entity.DialogRow
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