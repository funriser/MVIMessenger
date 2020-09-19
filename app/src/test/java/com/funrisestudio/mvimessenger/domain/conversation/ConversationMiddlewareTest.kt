package com.funrisestudio.mvimessenger.domain.conversation

import androidx.compose.ui.text.input.TextFieldValue
import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
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
        //Arrange
        val contactId = 1
        val mockedConversation = TestData.getMockedConversation()
        val conversationFlow = flow {
            emit(ConversationAction.ConversationReceived(mockedConversation))
        }
        whenever(getConversationUseCase.getFlow(contactId)).thenReturn(conversationFlow)
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.getProcessedActions()
            .take(2)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        conversationMiddleware.process(ConversationAction.LoadConversation(contactId))
        //Assert
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
        //Arrange
        val sendMsgAction = ConversationAction.SendMessage(1, "msg")
        val sendMessageResponseFlow = flow {
            emit(ConversationAction.MessageSent)
        }
        whenever(sendMessageUseCase.getFlow(sendMsgAction)).thenReturn(sendMessageResponseFlow)
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.getProcessedActions()
            .take(2)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        conversationMiddleware.process(sendMsgAction)
        //Assert
        val expectedOutput = listOf(sendMsgAction, ConversationAction.MessageSent)
        assertEquals(expectedOutput, output)
        verify(sendMessageUseCase).getFlow(sendMsgAction)
        verifyNoMoreInteractions(sendMessageUseCase)
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }

    @Test
    fun `should handle mark as read action`() = runBlockingTest {
        //Arrange
        val markAsReadAction = ConversationAction.MarkAsRead(1)
        val markAsReadResponseFlow = flow {
            emit(ConversationAction.MessagesMarkedAsRead)
        }
        whenever(markAsReadUseCase.getFlow(markAsReadAction)).thenReturn(markAsReadResponseFlow)
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.getProcessedActions()
            .take(1)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        conversationMiddleware.process(markAsReadAction)
        //Assert
        val expectedOutput = listOf(ConversationAction.MessagesMarkedAsRead)
        assertEquals(expectedOutput, output)
        verify(markAsReadUseCase).getFlow(markAsReadAction)
        verifyNoMoreInteractions(markAsReadUseCase)
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(sendMessageUseCase)
    }

    @Test
    fun `should not react to unsupported actions`() = runBlockingTest {
        //Arrange
        val unsupportedAction = ConversationAction.MessageInputChanged(TextFieldValue())
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.getProcessedActions()
            .take(1)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        conversationMiddleware.process(unsupportedAction)
        //Assert
        assertEquals(output, listOf(unsupportedAction))
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(sendMessageUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }

}