package com.funrisestudio.buzzmessenger.ui.messenger

import androidx.ui.foundation.TextFieldValue
import com.funrisestudio.buzzmessenger.core.mvi.Reducer
import javax.inject.Inject

class ConversationReducer @Inject constructor(
    private val conversationViewDataMapper: ConversationViewDataMapper
): Reducer<ConversationAction, ConversationViewState> {

    override fun reduce(
        viewState: ConversationViewState,
        action: ConversationAction
    ): ConversationViewState {
        return when(action)  {
            is ConversationAction.ContactReceived -> {
                ConversationViewState.createContactReceived(action.contact)
            }
            is ConversationAction.ConversationReceived -> {
                viewState.copy(
                    messages = conversationViewDataMapper.getMessageViewDataList(action.messages)
                )
            }
            is ConversationAction.MessageInputChanged -> {
                viewState.copy(
                    messageInput = action.newInput
                )
            }
            is ConversationAction.SendMessage -> {
                viewState.copy(
                    messageInput = TextFieldValue("")
                )
            }
            else -> viewState
        }
    }

}