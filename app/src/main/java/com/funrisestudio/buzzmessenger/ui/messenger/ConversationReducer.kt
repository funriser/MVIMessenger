package com.funrisestudio.buzzmessenger.ui.messenger

import com.funrisestudio.buzzmessenger.core.mvi.Reducer
import javax.inject.Inject

class ConversationReducer @Inject constructor(): Reducer<ConversationAction, ConversationViewState> {

    override fun reduce(
        viewState: ConversationViewState,
        action: ConversationAction
    ): ConversationViewState {
        return when(action)  {
            is ConversationAction.SenderReceived -> {
                ConversationViewState.createSenderReceived(action.sender)
            }
        }
    }

}