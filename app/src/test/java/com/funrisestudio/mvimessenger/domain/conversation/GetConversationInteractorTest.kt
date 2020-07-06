package com.funrisestudio.mvimessenger.domain.conversation

import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.domain.entity.Message
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetConversationInteractorTest {

    private val conversationRepository: ConversationRepository = mock()
    private lateinit var interactor: GetConversationInteractor

    @Before
    fun setUp() {
        interactor = GetConversationInteractor(conversationRepository)
    }

    @Test
    fun `should fetch conversations successfully`() = runBlockingTest {
        val contactId = 1
        val mockedConversations =
            TestData.getMockedConversation()
        val testFlow = flow {
            emit(mockedConversations)
        }
        whenever(conversationRepository.getConversation(contactId))
            .thenReturn(testFlow)

        val expected = ConversationAction.ConversationReceived(mockedConversations)

        val res = interactor.getFlow(contactId).toList()
        assertEquals(listOf(expected), res)

        verify(conversationRepository).getConversation(contactId)
        verifyNoMoreInteractions(conversationRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun `should proceed with conversations exception`() = runBlockingTest {
        val contactId = 1
        val testFlow = flow<List<Message>> {
            throw IllegalStateException()
        }
        whenever(conversationRepository.getConversation(contactId))
            .thenReturn(testFlow)
        interactor.getFlow(contactId).toList()
    }

}