package com.funrisestudio.mvimessenger.ui.conversation

import androidx.compose.ui.text.input.TextFieldValue
import com.funrisestudio.mvimessenger.core.mvi.Action
import com.funrisestudio.mvimessenger.core.mvi.ViewState
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.entity.Message

sealed class ConversationAction : Action {
    data class ConversationReceived(val messages: List<Message>): ConversationAction()
    data class LoadConversation(val contactId: Int): ConversationAction()
    data class MessageInputChanged(val newInput: TextFieldValue): ConversationAction()
    data class SendMessage(val contactId: Int, val message: String): ConversationAction()
    data class MarkAsRead(val contactId: Int): ConversationAction()
    object MessageSent: ConversationAction()
    object MessagesMarkedAsRead: ConversationAction()
    object Loading: ConversationAction()
}

data class ConversationViewState(
    val contact: Contact?,
    val messages: List<MessageViewData>,
    val messageInput: TextFieldValue,
    val sendMessageEnabled: Boolean
): ViewState {

    companion object {

        fun createEmpty(): ConversationViewState {
            return ConversationViewState(
                contact = null,
                messages = emptyList(),
                messageInput = TextFieldValue(""),
                sendMessageEnabled = false
            )
        }

        fun createFromContact(contact: Contact) = ConversationViewState(
            contact = contact,
            messages = emptyList(),
            messageInput = TextFieldValue(""),
            sendMessageEnabled = false
        )

        fun createConversationReceived(
            contact: Contact?,
            messages: List<MessageViewData>
        ): ConversationViewState {
            return ConversationViewState(
                contact = contact,
                messages = messages,
                messageInput = TextFieldValue(""),
                sendMessageEnabled = false
            )
        }

    }

}