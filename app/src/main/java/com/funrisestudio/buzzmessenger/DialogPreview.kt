package com.funrisestudio.buzzmessenger

data class DialogPreview(
    val contact: Sender,
    val lastMessage: MessagePreview,
    val unreadCount: Int
)

data class MessagePreview(
    val text: String,
    val date: String
)