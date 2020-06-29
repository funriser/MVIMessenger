package com.funrisestudio.buzzmessenger.ui.messenger

import com.funrisestudio.buzzmessenger.core.mvi.Reducer
import javax.inject.Inject

class ConversationReducer @Inject constructor(): Reducer<ConversationAction, ConversationViewState> {

    override fun reduce(
        viewState: ConversationViewState,
        action: ConversationAction
    ): ConversationViewState {
        return when(action)  {
            is ConversationAction.ContactReceived -> {
                ConversationViewState.createContactReceived(action.contact)
            }
            is ConversationAction.ConversationReceived -> {
                ConversationViewState.createConversationReceived(
                    contact = viewState.contact,
                    messages = action.messages
                )
            }
            else -> viewState
        }
    }

}