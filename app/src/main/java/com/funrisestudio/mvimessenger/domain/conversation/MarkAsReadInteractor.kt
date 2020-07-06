package com.funrisestudio.mvimessenger.domain.conversation

import com.funrisestudio.mvimessenger.domain.FlowUseCase
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MarkAsReadUseCase : FlowUseCase<ConversationAction, ConversationAction.MarkAsRead>

class MarkAsReadInteractor @Inject constructor(
    private val conversationRepository: ConversationRepository
) : MarkAsReadUseCase {

    override fun getFlow(params: ConversationAction.MarkAsRead): Flow<ConversationAction> {
        return flow {
            conversationRepository.markMessagesAsRead(params.contactId)
            emit(ConversationAction.MessagesMarkedAsRead)
        }
    }

}