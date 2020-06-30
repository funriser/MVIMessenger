package com.funrisestudio.buzzmessenger.domain.dialogs

import com.funrisestudio.buzzmessenger.domain.Contact
import java.util.*

data class Dialog(
    val contact: Contact,
    val lastMessage: MessagePreview,
    val unreadCount: Int
)

data class MessagePreview(
    val text: String,
    val date: Date
)