package com.funrisestudio.mvimessenger.domain.conversation

import com.funrisestudio.mvimessenger.core.mvi.MiddleWare
import com.funrisestudio.mvimessenger.core.ofType
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class ConversationMiddleware @Inject constructor(
    private val getConversationUseCase: GetConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase
): MiddleWare<ConversationAction, ConversationViewState> {

    override fun bind(actions: Flow<ConversationAction>): Flow<ConversationAction> {
        return merge(
            conversationFlow(actions.ofType()),
            sendMessageFlow(actions.ofType()),
            markAsReadFlow(actions.ofType())
        ).flowOn(Dispatchers.IO)
    }

    private fun conversationFlow(actions: Flow<ConversationAction.LoadConversation>): Flow<ConversationAction> {
        return actions.flatMapMerge {
                getConversationUseCase.getFlow(it.contactId)
                    .onStart { emit(ConversationAction.Loading) }
            }
    }

    private fun sendMessageFlow(actions: Flow<ConversationAction.SendMessage>): Flow<ConversationAction> {
        return actions.flatMapMerge {
                sendMessageUseCase.getFlow(it)
            }
    }

    private fun markAsReadFlow(actions: Flow<ConversationAction.MarkAsRead>): Flow<ConversationAction> {
        return actions.flatMapMerge {
                markAsReadUseCase.getFlow(it)
            }
    }

}