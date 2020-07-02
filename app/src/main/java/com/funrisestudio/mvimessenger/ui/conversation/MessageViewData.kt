package com.funrisestudio.mvimessenger.ui.conversation

import com.funrisestudio.mvimessenger.domain.entity.Message

data class MessageViewData(
    val message: Message,
    val formattedDate: String
)