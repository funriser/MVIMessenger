package com.funrisestudio.mvimessenger.ui.conversation

import com.funrisestudio.mvimessenger.domain.entity.Message
import com.funrisestudio.mvimessenger.ui.utils.DateFormat
import javax.inject.Inject

class ConversationViewDataMapper @Inject constructor(
    private val dateFormat: DateFormat
) {

    fun getMessageViewDataList(messages: List<Message>): List<MessageViewData> {
        return messages.map {
            MessageViewData(it, dateFormat.timeFormat(it.timestamp))
        }
    }

}