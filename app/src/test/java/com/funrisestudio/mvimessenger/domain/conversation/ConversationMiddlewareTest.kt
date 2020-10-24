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

    /*@Before
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
        val actionsInputFlow = flow<ConversationAction> {
            emit(ConversationAction.LoadConversation(contactId))
        }
        val mockedConversation = TestData.getMockedConversation()
        val conversationFlow = flow {
            emit(ConversationAction.ConversationReceived(mockedConversation))
        }
        whenever(getConversationUseCase.getFlow(contactId)).thenReturn(conversationFlow)
        //Act
        val output = mutableListOf<ConversationAction>()

        conversationMiddleware.bind(actionsInputFlow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .launchIn(this)

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
        val sendMessageAction = ConversationAction.SendMessage(1, "msg")
        val actionsInputFLow = flow<ConversationAction> {
            emit(sendMessageAction)
        }
        val sendMessageResponseFlow = flow {
            emit(ConversationAction.MessageSent)
        }
        whenever(sendMessageUseCase.getFlow(sendMessageAction)).thenReturn(sendMessageResponseFlow)
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.bind(actionsInputFLow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        //Assert
        val expectedOutput = listOf(ConversationAction.MessageSent)
        assertEquals(expectedOutput, output)
        verify(sendMessageUseCase).getFlow(sendMessageAction)
        verifyNoMoreInteractions(sendMessageUseCase)
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }

    @Test
    fun `should handle mark as read action`() = runBlockingTest {
        //Arrange
        val markAsReadAction = ConversationAction.MarkAsRead(1)
        val actionsInputFlow = flow<ConversationAction> {
            emit(markAsReadAction)
        }
        val markAsReadResponseFlow = flow {
            emit(ConversationAction.MessagesMarkedAsRead)
        }
        whenever(markAsReadUseCase.getFlow(markAsReadAction)).thenReturn(markAsReadResponseFlow)
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.bind(actionsInputFlow)
            .take(1)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
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
        val actionsInputFlow = flow<ConversationAction> {
            emit(unsupportedAction)
        }
        //Act
        val actual = conversationMiddleware.bind(actionsInputFlow).toList()
        //Assert
        assertEquals(emptyList<ConversationAction>(), actual)
        verifyZeroInteractions(getConversationUseCase)
        verifyZeroInteractions(sendMessageUseCase)
        verifyZeroInteractions(markAsReadUseCase)
    }*/

}