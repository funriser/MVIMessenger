package com.funrisestudio.mvimessenger.domain

import com.funrisestudio.mvimessenger.domain.conversation.ConversationRepository
import com.funrisestudio.mvimessenger.domain.conversation.SendMessageUseCase
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SendMessagesUseCaseTest {

    private val conversationRepository: ConversationRepository = mock()
    private lateinit var interactor: SendMessageUseCase

    @Before
    fun setUp() {
        interactor = SendMessageUseCase(conversationRepository)
    }

    @Test
    fun `should send message successfully`() = runBlockingTest {
        val contactId = 1
        val text = "msgText"
        whenever(conversationRepository.sendMessage(contactId, text)).thenReturn(Unit)

        val res = interactor.getFlow(ConversationAction.SendMessage(contactId, text)).toList()
        Assert.assertEquals(listOf(ConversationAction.MessageSent), res)

        verify(conversationRepository).sendMessage(contactId, text)
        verifyNoMoreInteractions(conversationRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun `should proceed with conversations exception`() = runBlockingTest {
        val contactId = 1
        val text = "msgText"
        doThrow(IllegalStateException()).whenever(conversationRepository)
            .sendMessage(contactId, text)
        interactor.getFlow(ConversationAction.SendMessage(contactId, text)).toList()
    }

}