package com.funrisestudio.buzzmessenger.domain.conversation

import com.funrisestudio.buzzmessenger.domain.FlowUseCase
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetConversationUseCase @Inject constructor(
    private val conversationRepository: ConversationRepository
): FlowUseCase<ConversationAction, Int>() {

    override fun getFlow(params: Int): Flow<ConversationAction> {
        return conversationRepository.getConversation(params)
            .map {
                ConversationAction.ConversationReceived(it)
            }
    }

}