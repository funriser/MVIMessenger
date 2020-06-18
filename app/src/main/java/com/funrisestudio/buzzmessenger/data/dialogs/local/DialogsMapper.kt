package com.funrisestudio.buzzmessenger.data.dialogs.local

import com.funrisestudio.buzzmessenger.domain.Dialog
import com.funrisestudio.buzzmessenger.domain.MessagePreview
import com.funrisestudio.buzzmessenger.domain.Sender
import com.funrisestudio.buzzmessenger.data.room.entity.DialogRow
import javax.inject.Inject

class DialogsMapper @Inject constructor() {

    fun getDialogs(list: List<DialogRow>): List<Dialog> {
        return list.map { getDialogPreview(it) }
    }

    private fun getDialogPreview(row: DialogRow): Dialog {
        return with(row) {
            Dialog(
                contact = Sender(
                    id = sender.id,
                    name = sender.name,
                    avatar = sender.avatar
                ),
                lastMessage = MessagePreview(
                    text = lastMessage,
                    date = lastMessageDate
                ),
                unreadCount = 1
            )
        }
    }

}