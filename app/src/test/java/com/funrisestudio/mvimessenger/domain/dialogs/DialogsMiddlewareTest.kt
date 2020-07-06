package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class DialogsMiddlewareTest {

    private val getDialogsUseCase: GetDialogsUseCase = mock()
    private lateinit var dialogsMiddleware: DialogsMiddleware

    @Before
    fun setUp() {
        dialogsMiddleware = DialogsMiddleware(getDialogsUseCase)
    }

    @Test
    fun `should handle load dialogs action`() = runBlockingTest {
        val mockedDialogs = TestData.getMockedDialogs()

        val inputStream = flow {
            emit(DialogsAction.LoadDialogs)
        }

        val dialogsFlow = flow {
            emit(DialogsAction.DialogsLoaded(mockedDialogs))
        }
        whenever(getDialogsUseCase.getFlow(Unit)).thenReturn(dialogsFlow)

        val output = dialogsMiddleware.bind(inputStream).toList()

        assertTrue(output.contains(DialogsAction.DialogsLoaded(mockedDialogs)))

        verify(getDialogsUseCase).getFlow(Unit)
        verifyNoMoreInteractions(getDialogsUseCase)
    }

    @Test
    fun `should emit dialogs loading action`() = runBlockingTest {
        val mockedDialogs = TestData.getMockedDialogs()

        val inputStream = flow {
            emit(DialogsAction.LoadDialogs)
        }

        val dialogsFlow = flow {
            emit(DialogsAction.DialogsLoaded(mockedDialogs))
        }
        whenever(getDialogsUseCase.getFlow(Unit)).thenReturn(dialogsFlow)

        val output = dialogsMiddleware.bind(inputStream).toList()

        assertTrue(output.contains(DialogsAction.Loading))

        verify(getDialogsUseCase).getFlow(Unit)
        verifyNoMoreInteractions(getDialogsUseCase)
    }

    @Test
    fun `should not react to unsupported actions`() = runBlockingTest {
        val inputStream = flow {
            //mock unsupported action
            emit(DialogsAction.DialogsError(IllegalStateException()))
        }

        val output = dialogsMiddleware.bind(inputStream).toList()

        assertTrue(output.isEmpty())

        verifyZeroInteractions(getDialogsUseCase)
    }

}