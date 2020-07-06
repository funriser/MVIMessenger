package com.funrisestudio.mvimessenger.domain

import com.funrisestudio.mvimessenger.data.contacts
import com.funrisestudio.mvimessenger.domain.messages.MessengerRepository
import com.funrisestudio.mvimessenger.domain.messages.ReceiveMessageInteractor
import com.funrisestudio.mvimessenger.domain.messages.ReceiveMessageUseCase
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ReceiveMessageInteractorTest {

    private val messengerRepository: MessengerRepository = mock()
    private lateinit var interactor: ReceiveMessageInteractor

    @Before
    fun setUp() {
        interactor = ReceiveMessageInteractor(messengerRepository)
    }

    @Test
    fun `should fetch conversations successfully`() = runBlockingTest {
        val contact = contacts[0]
        val text = "Some text"
        whenever(messengerRepository.saveMessage(contact, text)).thenReturn(Unit)

        interactor.getFlow(ReceiveMessageUseCase.Params(contact, text)).collect {  }

        verify(messengerRepository).saveMessage(contact, text)
        verifyNoMoreInteractions(messengerRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun `should proceed with conversations exception`() = runBlockingTest {
        val contact = contacts[0]
        val text = "Some text"
        doThrow(IllegalStateException())
            .whenever(messengerRepository).saveMessage(contact, text)
        interactor.getFlow(ReceiveMessageUseCase.Params(contact, text)).collect {  }
    }

}