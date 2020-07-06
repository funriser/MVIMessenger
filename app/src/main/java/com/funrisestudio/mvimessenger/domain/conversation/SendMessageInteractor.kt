package com.funrisestudio.mvimessenger.domain.conversation

import com.funrisestudio.mvimessenger.domain.FlowUseCase
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SendMessageUseCase: FlowUseCase<ConversationAction, ConversationAction.SendMessage>

class SendMessageInteractor @Inject constructor(
    private val conversationRepository: ConversationRepository
): SendMessageUseCase {

    override fun getFlow(params: ConversationAction.SendMessage): Flow<ConversationAction> {
        return flow {
            conversationRepository.sendMessage(params.contactId, params.message)
            emit(ConversationAction.MessageSent)
        }
    }

}