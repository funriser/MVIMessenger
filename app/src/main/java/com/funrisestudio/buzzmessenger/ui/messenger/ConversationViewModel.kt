package com.funrisestudio.buzzmessenger.ui.messenger

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.ui.foundation.TextFieldValue
import com.funrisestudio.buzzmessenger.core.SingleLiveEvent
import com.funrisestudio.buzzmessenger.core.mvi.Store
import com.funrisestudio.buzzmessenger.core.navigation.ToMessages
import com.funrisestudio.buzzmessenger.domain.Contact
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConversationViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val store: Store<ConversationAction, ConversationViewState>
) : ViewModel() {

    private val contact: Contact =
        savedStateHandle.get(ToMessages.KEY_CONTACT)
            ?: throw IllegalStateException("Sender is not defined")

    private val _viewState = MutableLiveData<ConversationViewState>()
    val viewState: LiveData<ConversationViewState> = _viewState

    private val _generateResponse = SingleLiveEvent<Int>()
    val generateResponse: LiveData<Int> = _generateResponse

    init {
        store.wire(viewModelScope)
        store.observeViewState()
            .distinctUntilChanged()
            .onEach {
                _viewState.value = it
            }
            .launchIn(viewModelScope)
        store.processAction(ConversationAction.ContactReceived(contact))
        store.processAction(ConversationAction.LoadConversation(contact.id))
        initMessageReader()
    }

    //mark all received messages as read and send messages in response
    private fun initMessageReader() {
        store.interceptActions()
            .onEach {
                when(it) {
                    is ConversationAction.ConversationReceived -> {
                        store.processAction(ConversationAction.MarkAsRead(contact.id))
                    }
                    is ConversationAction.MessageSent -> {
                        _generateResponse.value = contact.id
                    }
                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    fun onMessageInputChanged(newInput: TextFieldValue) {
        store.processAction(ConversationAction.MessageInputChanged(newInput))
    }

    fun onSendMessage() {
        val currState = _viewState.value?:return
        val action = ConversationAction.SendMessage(contact.id, currState.messageInput.text)
        store.processAction(action)
    }

}