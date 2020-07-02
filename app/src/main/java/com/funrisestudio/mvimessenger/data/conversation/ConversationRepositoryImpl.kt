package com.funrisestudio.mvimessenger.data.conversation

import com.funrisestudio.mvimessenger.data.conversation.local.ConversationLocalSource
import com.funrisestudio.mvimessenger.domain.conversation.ConversationRepository
import com.funrisestudio.mvimessenger.domain.entity.Message
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

    override suspend fun markMessagesAsRead(contactId: Int) {
        conversationLocalSource.markMessagesAsRead(contactId)
    }

}