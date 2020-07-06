package com.funrisestudio.mvimessenger.domain.conversation

import androidx.ui.foundation.TextFieldValue
import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class ConversationMiddlewareTest {

    private val getConversationUseCase: GetConversationUseCase = mock()
    private val sendMessageUseCase: SendMessageUseCase = mock()
    private val markAsReadUseCase: MarkAsReadUseCase = mock()

    private lateinit var conversationMiddleware: ConversationMiddleware

    @Before
    fun setUp() {
        conversationMiddleware = ConversationMiddleware(
            getConversationUseCase,
            sendMessageUseCase,
            markAsReadUseCase
        )
    }

    @Test
    fun `should handle load conversation action`() = runBlockingTest {
        val contactId = 1
        val mockedConversation = TestData.getMockedConversation()

        val inputStream = flow {
            emit(ConversationAction.LoadConversation(contactId))
        }

        val conversationFlow = flow {
            emit(ConversationAction.ConversationReceived(mockedConversation))
        }
        whenever(getConversationUseCase.getFlow(contactId)).thenReturn(conversationFlow)

        val output = conversationMiddleware.bind(inputStream).toList()

        val expectedOutput = listOf(
            ConversationAction.Loading,
            ConversationAction.ConversationReceived(mockedConversation)
        )

        assertEquals(expectedOutput, output)

        verify(getConversationUseCase).getFlow(contactId)
        verifyNoMoreInteractions(getConversationUseCase)
        verifyZeroInteractions(sendMessageUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }

    @Test
    fun `should handle send message action`() = runBlockingTest {
        val sendMsgAction = ConversationAction.SendMessage(1, "msg")

        val inputStream = flow {
            emit(sendMsgAction)
        }

        val sendMessageResponseFlow = flow {
            emit(ConversationAction.MessageSent)
        }
        whenever(sendMessageUseCase.getFlow(sendMsgAction)).thenReturn(sendMessageResponseFlow)

        val output = conversationMiddleware.bind(inputStream).toList()

        val expectedOutput = listOf(ConversationAction.MessageSent)

        assertEquals(expectedOutput, output)

        verify(sendMessageUseCase).getFlow(sendMsgAction)
        verifyNoMoreInteractions(sendMessageUseCase)
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }

    @Test
    fun `should handle mark as read action`() = runBlockingTest {
        val markAsReadAction = ConversationAction.MarkAsRead(1)

        val inputStream = flow {
            emit(markAsReadAction)
        }

        val markAsReadResponseFlow = flow {
            emit(ConversationAction.MessagesMarkedAsRead)
        }
        whenever(markAsReadUseCase.getFlow(markAsReadAction)).thenReturn(markAsReadResponseFlow)

        val output = conversationMiddleware.bind(inputStream).toList()

        val expectedOutput = listOf(ConversationAction.MessagesMarkedAsRead)

        assertEquals(expectedOutput, output)

        verify(markAsReadUseCase).getFlow(markAsReadAction)
        verifyNoMoreInteractions(markAsReadUseCase)
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(sendMessageUseCase)
    }

    @Test
    fun `should not react to unsupported actions`() = runBlockingTest {
        val inputStream = flow {
            //mock unsupported action
            emit(ConversationAction.MessageInputChanged(TextFieldValue()))
        }

        val output = conversationMiddleware.bind(inputStream).toList()

        assertTrue(output.isEmpty())

        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(sendMessageUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }

}