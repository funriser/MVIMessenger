package com.funrisestudio.buzzmessenger.ui.messenger

import com.funrisestudio.buzzmessenger.core.mvi.Action
import com.funrisestudio.buzzmessenger.core.mvi.ViewState
import com.funrisestudio.buzzmessenger.domain.Sender

sealed class ConversationAction : Action {
    data class SenderReceived(val sender: Sender): ConversationAction()
}

data class ConversationViewState(
    val sender: Sender?
): ViewState {

    companion object {

        fun createEmpty(): ConversationViewState {
            return ConversationViewState(null)
        }

        fun createSenderReceived(sender: Sender): ConversationViewState {
            return ConversationViewState(sender)
        }

    }

}