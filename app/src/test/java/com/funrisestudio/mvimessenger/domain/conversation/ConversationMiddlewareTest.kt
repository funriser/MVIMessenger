package com.funrisestudio.mvimessenger.domain.conversation

import androidx.compose.ui.text.input.TextFieldValue
import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class ConversationMiddlewareTest {

    private val conversationRepository: ConversationRepository = mock()
    private lateinit var conversationMiddleware: ConversationMiddleware

    @Before
    fun setUp() {
        conversationMiddleware = ConversationMiddleware(conversationRepository)
    }

    @Test
    fun `should handle load conversation action`() = runBlocking<Unit> {
        //Arrange
        val contactId = 1
        val actionsInputFlow = flow<ConversationAction> {
            emit(ConversationAction.LoadConversation(contactId))
        }
        val mockedConversation = TestData.getMockedConversation()
        val conversationFlow = flow {
            emit(mockedConversation)
        }
        whenever(conversationRepository.getConversation(contactId)).thenReturn(conversationFlow)
        //Act
        val output = mutableListOf<ConversationAction>()

        conversationMiddleware.bind(actionsInputFlow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .toList()

        //Assert
        val expectedOutput = listOf(
            ConversationAction.Loading,
            ConversationAction.ConversationReceived(mockedConversation)
        )
        assertEquals(expectedOutput, output)
        verify(conversationRepository).getConversation(contactId)
    }

    @Test
    fun `should handle send message action`() = runBlocking<Unit> {
        //Arrange
        val contactId = 1
        val message = "msg"
        val sendMessageAction = ConversationAction.SendMessage(contactId, message)
        val actionsInputFLow = flow<ConversationAction> {
            emit(sendMessageAction)
        }
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.bind(actionsInputFLow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .toList()
        //Assert
        val expectedOutput = listOf(ConversationAction.MessageSent)
        assertEquals(expectedOutput, output)
        verify(conversationRepository).sendMessage(contactId, message)
    }

    @Test
    fun `should handle mark as read action`() = runBlocking<Unit> {
        //Arrange
        val contactId = 1
        val markAsReadAction = ConversationAction.MarkAsRead(contactId)
        val actionsInputFlow = flow<ConversationAction> {
            emit(markAsReadAction)
        }
        //Act
        val output = mutableListOf<ConversationAction>()
        conversationMiddleware.bind(actionsInputFlow)
            .take(1)
            .onEach {
                output.add(it)
            }
            .toList()
        //Assert
        val expectedOutput = listOf(ConversationAction.MessagesMarkedAsRead)
        assertEquals(expectedOutput, output)
        verify(conversationRepository).markMessagesAsRead(contactId)
    }

    @Test
    fun `should not react to unsupported actions`() = runBlocking {
        //Arrange
        val unsupportedAction = ConversationAction.MessageInputChanged(TextFieldValue())
        val actionsInputFlow = flow<ConversationAction> {
            emit(unsupportedAction)
        }
        //Act
        val actual = conversationMiddleware.bind(actionsInputFlow).toList()
        //Assert
        assertEquals(emptyList<ConversationAction>(), actual)
    }

}