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
    private val conversationRepository: ConversationRepository
) : MiddleWare<ConversationAction, ConversationViewState> {

    override fun bind(actions: Flow<ConversationAction>): Flow<ConversationAction> {
        return merge(
            conversationFlow(actions.ofType()),
            sendMessageFlow(actions.ofType()),
            markAsReadFlow(actions.ofType())
        ).flowOn(Dispatchers.IO)
    }

    private fun conversationFlow(
        actions: Flow<ConversationAction.LoadConversation>
    ) = actions
        .flatMapLatest {
            conversationRepository.getConversation(it.contactId)
                .map { messages ->
                    ConversationAction.ConversationReceived(messages)
                }
                .ofType<ConversationAction>()
                .onStart {
                    emit(ConversationAction.Loading)
                }
        }


    private fun sendMessageFlow(
        actions: Flow<ConversationAction.SendMessage>
    ) = actions
        .onEach { sendMsgAction ->
            conversationRepository.sendMessage(sendMsgAction.contactId, sendMsgAction.message)
        }.map {
            ConversationAction.MessageSent
        }

    private fun markAsReadFlow(
        actions: Flow<ConversationAction.MarkAsRead>
    ) = actions
        .onEach { markAsReadAction ->
            conversationRepository.markMessagesAsRead(markAsReadAction.contactId)
        }.map {
            ConversationAction.MessagesMarkedAsRead
        }

}