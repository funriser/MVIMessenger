package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
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
class GetDialogsInteractorTest {

    private val dialogsRepository: DialogsRepository = mock()
    private lateinit var interactor: GetDialogsInteractor

    @Before
    fun setUp() {
        interactor = GetDialogsInteractor(dialogsRepository)
    }

    @Test
    fun `should fetch dialogs successfully`() = runBlockingTest {
        val mockedDialogs =
            TestData.getMockedDialogs()
        val testFlow = flow {
            emit(mockedDialogs)
        }
        whenever(dialogsRepository.getDialogs()).thenReturn(testFlow)

        val expected = DialogsAction.DialogsLoaded(mockedDialogs)

        val res = interactor.getFlow(Unit).toList()
        assertEquals(listOf(expected), res)

        verify(dialogsRepository).getDialogs()
        verifyNoMoreInteractions(dialogsRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun `should proceed with dialogs exception`() = runBlockingTest {
        val testFlow = flow<List<Dialog>> {
            throw IllegalStateException()
        }
        whenever(dialogsRepository.getDialogs()).thenReturn(testFlow)
        interactor.getFlow(Unit).toList()
    }

}