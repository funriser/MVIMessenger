package com.funrisestudio.mvimessenger.ui.conversations

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun `should init store relationship and launch loading`() {
        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = MutableStateFlow(initialState)
        whenever(store.viewStateFlow).thenReturn(mockedStateFlow)
        viewModel = ConversationViewModel(savedStateHandle, store)
        assertEquals(store.viewStateFlow, viewModel.viewState)
        verify(store).process(ConversationAction.LoadConversation(contact.id))
    }

    @Test
    fun `should launch mark as read action after non empty conversation received`() {
        val initialState = ConversationViewState(
            contact = null,
            messages = TestData.getMessagesViewData(),
            messageInput = TextFieldValue(""),
            sendMessageEnabled = false
        )
        whenever(store.viewStateFlow).thenReturn(MutableStateFlow(initialState))

        viewModel = ConversationViewModel(savedStateHandle, store)

        //wait until mocked action is processed
        Thread.sleep(100)
        verify(store).process(ConversationAction.MarkAsRead(contact.id))
    }

    @Test
    fun `should update generate response live date after new message sent`() {
        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = MutableStateFlow(initialState)
        whenever(store.viewStateFlow).thenReturn(mockedStateFlow)

        viewModel = ConversationViewModel(savedStateHandle, store)
        viewModel.onSendMessage()

        val actualCommand = viewModel.generateResponse.awaitValue()
        assertEquals(contact.id, actualCommand)
    }

    @Test
    fun `should respond with new action after message input changed`() {
        val mockedNewInput = TextFieldValue()

        val initialState = ConversationViewState.createEmpty()
        val mockedStateFlow = MutableStateFlow(initialState)
        whenever(store.viewStateFlow).thenReturn(mockedStateFlow)

        viewModel = ConversationViewModel(savedStateHandle, store)

        viewModel.onMessageInputChanged(mockedNewInput)

        verify(store).process(ConversationAction.MessageInputChanged(mockedNewInput))
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
        val mockedStateFlow = MutableStateFlow(initialState)
        whenever(store.viewStateFlow).thenReturn(mockedStateFlow)

        viewModel = ConversationViewModel(savedStateHandle, store)

        viewModel.onSendMessage()

        verify(store).process(ConversationAction.SendMessage(contact.id, mockedInput.text))
    }

}