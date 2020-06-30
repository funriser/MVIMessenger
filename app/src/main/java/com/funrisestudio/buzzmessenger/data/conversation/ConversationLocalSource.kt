package com.funrisestudio.buzzmessenger.data.conversation

import com.funrisestudio.buzzmessenger.data.USER_ID
import com.funrisestudio.buzzmessenger.data.room.MessagesDao
import com.funrisestudio.buzzmessenger.data.room.entity.MessageRow
import com.funrisestudio.buzzmessenger.domain.entity.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ConversationLocalSource @Inject constructor(
    private val messengerDao: MessagesDao,
    private val conversationMapper: ConversationMapper
) {

    fun getConversation(contactId: Int): Flow<List<Message>> {
        return messengerDao.getConversation(contactId)
            .map {
                conversationMapper.getMessages(it)
            }
    }

    suspend fun sendMessage(contactId: Int, text: String) {
        val messageRow = MessageRow(
            senderId = USER_ID,
            receiverId = contactId,
            message = text,
            timestamp = Date(),
            isRead = true
        )
        return messengerDao.insertMessage(messageRow)
    }

    suspend fun markMessagesAsRead(contactId: Int) {
        messengerDao.markMessagesAsRead(contactId)
    }

}