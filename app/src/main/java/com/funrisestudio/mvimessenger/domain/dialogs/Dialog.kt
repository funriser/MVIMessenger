package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.entity.Contact
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