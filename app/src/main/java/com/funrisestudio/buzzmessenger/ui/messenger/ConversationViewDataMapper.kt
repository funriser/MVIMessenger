package com.funrisestudio.buzzmessenger.ui.messenger

import com.funrisestudio.buzzmessenger.domain.entity.Message
import com.funrisestudio.buzzmessenger.ui.utils.DateFormat
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