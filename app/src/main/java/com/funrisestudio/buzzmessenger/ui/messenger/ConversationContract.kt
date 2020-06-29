package com.funrisestudio.buzzmessenger.ui.messenger

import com.funrisestudio.buzzmessenger.core.mvi.Action
import com.funrisestudio.buzzmessenger.core.mvi.ViewState
import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.domain.entity.Message

sealed class ConversationAction : Action {
    data class ContactReceived(val contact: Contact): ConversationAction()
    data class ConversationReceived(val messages: List<Message>): ConversationAction()
    data class LoadConversation(val contactId: Int): ConversationAction()
    object Loading: ConversationAction()
}

data class ConversationViewState(
    val contact: Contact?,
    val messages: List<Message>
): ViewState {

    companion object {

        fun createEmpty(): ConversationViewState {
            return ConversationViewState(
                contact = null,
                messages = emptyList()
            )
        }

        fun createContactReceived(contact: Contact): ConversationViewState {
            return ConversationViewState(
                contact = contact,
                messages = emptyList()
            )
        }

        fun createConversationReceived(
            contact: Contact?,
            messages: List<Message>
        ): ConversationViewState {
            return ConversationViewState(
                contact = contact,
                messages = messages
            )
        }

    }

}