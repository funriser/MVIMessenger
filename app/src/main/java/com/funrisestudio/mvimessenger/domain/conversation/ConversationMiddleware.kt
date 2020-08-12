package com.funrisestudio.mvimessenger.domain.conversation

import com.funrisestudio.mvimessenger.core.mvi.MiddleWare
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class ConversationMiddleware @Inject constructor(
    private val getConversationUseCase: GetConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase
): MiddleWare<ConversationAction, ConversationViewState> {

    private val allActions = BroadcastChannel<ConversationAction>(Channel.BUFFERED)

    override fun process(action: ConversationAction) {
        allActions.offer(action)
    }

    override fun getProcessedActions(): Flow<ConversationAction> {
        return allActions.asFlow()
            .flatMapMerge {
                when(it) {
                    is ConversationAction.LoadConversation -> {
                        getConversationUseCase.getFlow(it.contactId)
                            .onStart { emit(ConversationAction.Loading) }
                    }
                    is ConversationAction.SendMessage -> {
                        sendMessageUseCase.getFlow(it)
                            .onStart { emit(it) }
                    }
                    is ConversationAction.MarkAsRead -> {
                        markAsReadUseCase.getFlow(it)
                    }
                    else -> flow { emit(it) }
                }
            }
    }

}