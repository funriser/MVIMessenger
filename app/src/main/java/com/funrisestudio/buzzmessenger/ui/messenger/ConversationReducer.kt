package com.funrisestudio.buzzmessenger.ui.messenger

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
                ConversationViewState.createConversationReceived(
                    contact = viewState.contact,
                    messages = conversationViewDataMapper.getMessageViewDataList(action.messages)
                )
            }
            else -> viewState
        }
    }

}