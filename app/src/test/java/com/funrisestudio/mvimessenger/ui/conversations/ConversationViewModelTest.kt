package com.funrisestudio.mvimessenger.ui.conversations

import androidx.lifecycle.SavedStateHandle
import androidx.ui.foundation.TextFieldValue
import com.funrisestudio.mvimessenger.core.mvi.Store
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.ViewModelTest
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewModel
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewState
import com.funrisestudio.mvimessenger.utils.awaitValue
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class ConversationViewModelTest: ViewModelTest() {

    private val contact = TestData.getMockedContact()

    private val store: Store<ConversationAction, ConversationViewState> = mock()
    private val savedStateHandle = SavedStateHandle(mapOf(ToMessages.KEY_CONTACT to contact))

    private lateinit var viewModel: ConversationViewModel

    @Test
    fun `should observe view state after initialized`() {
        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = flow {
            emit(initialState)
        }
        whenever(store.observeViewState()).thenReturn(mockedStateFlow)
        whenever(store.interceptActions()).thenReturn(flow {  })

        viewModel = ConversationViewModel(savedStateHandle, store)

        val actualViewState = viewModel.viewState.awaitValue()

        assertEquals(initialState, actualViewState)
        verify(store).wire(any())
        verify(store).observeViewState()
    }

    @Test
    fun `should update initial state and launch loading after initialized`() {
        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = flow {
            emit(initialState)
        }
        whenever(store.observeViewState()).thenReturn(mockedStateFlow)
        whenever(store.interceptActions()).thenReturn(flow {  })

        viewModel = ConversationViewModel(savedStateHandle, store)

        verify(store).processAction(ConversationAction.ContactReceived(contact))
        verify(store).processAction(ConversationAction.LoadConversation(contact.id))
    }

    @Test
    fun `should launch mark as read action after conversation received`() {
        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = flow {
            emit(initialState)
        }
        whenever(store.observeViewState()).thenReturn(mockedStateFlow)

        val mockedActionsFlow = flow {
            emit(ConversationAction.ConversationReceived(emptyList()))
        }
        whenever(store.interceptActions()).thenReturn(mockedActionsFlow)

        viewModel = ConversationViewModel(savedStateHandle, store)

        //wait until mocked action is processed
        Thread.sleep(100)

        verify(store).processAction(ConversationAction.MarkAsRead(contact.id))
    }

    @Test
    fun `should update generate response live date after new message sent`() {
        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = flow {
            emit(initialState)
        }
        whenever(store.observeViewState()).thenReturn(mockedStateFlow)

        val mockedActionsFlow = flow {
            emit(ConversationAction.MessageSent)
        }
        whenever(store.interceptActions()).thenReturn(mockedActionsFlow)

        viewModel = ConversationViewModel(savedStateHandle, store)

        val actualCommand = viewModel.generateResponse.awaitValue()

        assertEquals(contact.id, actualCommand)
    }

    @Test
    fun `should respond with new action after message input changed`() {
        val mockedNewInput = TextFieldValue()

        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = flow {
            emit(initialState)
        }
        whenever(store.observeViewState()).thenReturn(mockedStateFlow)
        whenever(store.interceptActions()).thenReturn(flow {  })

        viewModel = ConversationViewModel(savedStateHandle, store)

        viewModel.onMessageInputChanged(mockedNewInput)

        verify(store).processAction(ConversationAction.MessageInputChanged(mockedNewInput))
    }

    @Test
    fun `should respond with send message action after corresponding btn clicked`() {
        val mockedInput = TextFieldValue("SomeText")
        val initialState = ConversationViewState(
            contact = contact,
            messages = listOf(),
            messageInput = mockedInput,
            sendMessageEnabled = true
        )
        val mockedStateFlow = flow {
            emit(initialState)
        }
        whenever(store.observeViewState()).thenReturn(mockedStateFlow)
        whenever(store.interceptActions()).thenReturn(flow {  })

        viewModel = ConversationViewModel(savedStateHandle, store)

        //wait until initial state is processed
        viewModel.viewState.awaitValue()

        viewModel.onSendMessage()

        verify(store).processAction(ConversationAction.SendMessage(contact.id, mockedInput.text))
    }

}