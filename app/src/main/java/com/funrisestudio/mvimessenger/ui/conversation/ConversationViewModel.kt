package com.funrisestudio.mvimessenger.ui.conversation

import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.funrisestudio.mvimessenger.core.SingleLiveEvent
import com.funrisestudio.mvimessenger.core.mvi.Store
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.domain.entity.Contact
import kotlinx.coroutines.flow.*

class ConversationViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val store: Store<ConversationAction, ConversationViewState>
) : ViewModel() {

    private val contact: Contact =
        savedStateHandle.get(ToMessages.KEY_CONTACT)
            ?: throw IllegalStateException("Sender is not defined")

    private val initialState = ConversationViewState.createFromContact(contact)

    val viewState: StateFlow<ConversationViewState>

    private val _generateResponse = SingleLiveEvent<Int>()
    val generateResponse: LiveData<Int> = _generateResponse

    init {
        store.init(viewModelScope, initialState)
        viewState = store.viewStateFlow
        store.process(ConversationAction.LoadConversation(contact.id))
        initMessageReader()
    }

    //mark all received messages as read and send messages in response
    private fun initMessageReader() {
        store.viewStateFlow
            .filter { vState ->
                vState.messages.isNotEmpty()
            }
            .onEach {
                store.process(ConversationAction.MarkAsRead(contact.id))
            }
            .launchIn(viewModelScope)
    }

    fun onMessageInputChanged(newInput: TextFieldValue) {
        store.process(ConversationAction.MessageInputChanged(newInput))
    }

    fun onSendMessage() {
        val action = ConversationAction.SendMessage(contact.id, viewState.value.messageInput.text)
        store.process(action)
        _generateResponse.value = contact.id
    }

}