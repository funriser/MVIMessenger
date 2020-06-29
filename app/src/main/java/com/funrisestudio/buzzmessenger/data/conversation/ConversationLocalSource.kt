package com.funrisestudio.buzzmessenger.data.conversation

import com.funrisestudio.buzzmessenger.data.room.MessagesDao
import com.funrisestudio.buzzmessenger.domain.entity.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

}