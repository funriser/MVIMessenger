package com.funrisestudio.mvimessenger.ui.conversations

import androidx.compose.ui.text.input.TextFieldValue
import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.funrisestudio.mvimessenger.ui.conversation.ConversationReducer
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewDataMapper
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConversationReducerTest {

    private val conversationViewDataMapper: ConversationViewDataMapper = mock()

    private lateinit var conversationReducer: ConversationReducer

    @Before
    fun setUp() {
        conversationReducer = ConversationReducer(conversationViewDataMapper)
    }

    @Test
    fun `should reduce conversation received action`() {
        val currViewState = ConversationViewState(
            contact = null,
            messages = listOf(),
            messageInput = TextFieldValue(),
            sendMessageEnabled = false
        )

        val mockedMsgViewData = TestData.getMessagesViewData()
        val mockedMessages = TestData.getMockedConversation()
        val testAction = ConversationAction.ConversationReceived(mockedMessages)

        whenever(conversationViewDataMapper.getMessageViewDataList(mockedMessages))
            .thenReturn(mockedMsgViewData)

        val actualResult = conversationReducer.reduce(currViewState, testAction)

        val expected = ConversationViewState(
            contact = null,
            messages = mockedMsgViewData,
            messageInput = TextFieldValue(),
            sendMessageEnabled = false
        )

        assertEquals(expected, actualResult)
    }

    @Test
    fun `should reduce message input action`() {
        val currViewState = ConversationViewState(
            contact = null,
            messages = listOf(),
            messageInput = TextFieldValue(),
            sendMessageEnabled = false
        )

        val mockedInput = TextFieldValue("Some text")
        val testAction = ConversationAction.MessageInputChanged(mockedInput)

        val actualResult = conversationReducer.reduce(currViewState, testAction)

        val expected = ConversationViewState(
            contact = null,
            messages = listOf(),
            messageInput = TextFieldValue("Some text"),
            sendMessageEnabled = true
        )

        assertEquals(expected, actualResult)
    }

    @Test
    fun `should reduce send message action`() {
        val currViewState = ConversationViewState(
            contact = null,
            messages = listOf(),
            messageInput = TextFieldValue(),
            sendMessageEnabled = false
        )

        val testAction = ConversationAction.SendMessage(1, "Some msg")

        val actualResult = conversationReducer.reduce(currViewState, testAction)

        val expected = ConversationViewState(
            contact = null,
            messages = listOf(),
            //empty after message is sent
            messageInput = TextFieldValue(),
            sendMessageEnabled = false
        )

        assertEquals(expected, actualResult)
    }

    @Test
    fun `should ignore not important actions`() {
        val mockedViewState = ConversationViewState.createEmpty()
        val testAction = ConversationAction.MessagesMarkedAsRead

        val actualResult = conversationReducer.reduce(mockedViewState, testAction)

        //view state not changed
        assertEquals(mockedViewState, actualResult)
    }

}