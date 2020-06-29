package com.funrisestudio.buzzmessenger.data.conversation

import com.funrisestudio.buzzmessenger.domain.conversation.ConversationRepository
import com.funrisestudio.buzzmessenger.domain.entity.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConversationRepositoryImpl @Inject constructor(
    private val conversationLocalSource: ConversationLocalSource
): ConversationRepository {

    override fun getConversation(contactId: Int): Flow<List<Message>> {
        return conversationLocalSource.getConversation(contactId)
    }

    override suspend fun sendMessage(contactId: Int, text: String) {
        return conversationLocalSource.sendMessage(contactId, text)
    }

}