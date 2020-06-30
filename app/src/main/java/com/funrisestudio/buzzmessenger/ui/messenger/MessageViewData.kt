package com.funrisestudio.buzzmessenger.ui.messenger

import com.funrisestudio.buzzmessenger.domain.entity.Message

data class MessageViewData(
    val message: Message,
    val formattedDate: String
)