package com.funrisestudio.mvimessenger.ui.conversation

import androidx.compose.ui.text.input.TextFieldValue
import com.funrisestudio.mvimessenger.core.mvi.Reducer
import javax.inject.Inject

class ConversationReducer @Inject constructor(
    private val conversationViewDataMapper: ConversationViewDataMapper
): Reducer<ConversationAction, ConversationViewState> {

    override fun reduce(
        viewState: ConversationViewState,
        action: ConversationAction
    ): ConversationViewState {
        return when(action)  {
            is ConversationAction.ConversationReceived -> {
                viewState.copy(
                    messages = conversationViewDataMapper.getMessageViewDataList(action.messages)
                )
            }
            is ConversationAction.MessageInputChanged -> {
                viewState.copy(
                    messageInput = action.newInput,
                    sendMessageEnabled = action.newInput.text.isNotEmpty()
                )
            }
            is ConversationAction.SendMessage -> {
                viewState.copy(
                    messageInput = TextFieldValue(""),
                    sendMessageEnabled = false
                )
            }
            else -> viewState
        }
    }

}