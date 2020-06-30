package com.funrisestudio.buzzmessenger.domain.conversation

import com.funrisestudio.buzzmessenger.core.mvi.MiddleWare
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationAction
import com.funrisestudio.buzzmessenger.ui.messenger.ConversationViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class ConversationMiddleware @Inject constructor(
    private val getConversationUseCase: GetConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase
): MiddleWare<ConversationAction, ConversationViewState>() {

    override fun bind(actionStream: Flow<ConversationAction>): Flow<ConversationAction> {
        return actionStream
            .filter {
                isHandled(it)
            }
            .flatMapMerge {
                when(it) {
                    is ConversationAction.LoadConversation -> {
                        getConversationUseCase.getFlow(it.contactId)
                            .onStart { emit(ConversationAction.Loading) }
                    }
                    is ConversationAction.SendMessage -> {
                        sendMessageUseCase.getFlow(it)
                    }
                    is ConversationAction.MarkAsRead -> {
                        markAsReadUseCase.getFlow(it)
                    }
                    else -> throw IllegalStateException("Action is not supported")
                }
            }
    }

    private fun isHandled(action: ConversationAction): Boolean {
        return action is ConversationAction.LoadConversation ||
                action is ConversationAction.SendMessage ||
                action is ConversationAction.MarkAsRead
    }

}