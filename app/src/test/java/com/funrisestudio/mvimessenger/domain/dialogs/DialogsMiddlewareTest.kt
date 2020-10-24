package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class DialogsMiddlewareTest {

    private val getDialogsUseCase: GetDialogsUseCase = mock()
    private lateinit var dialogsMiddleware: DialogsMiddleware

    /*@Before
    fun setUp() {
        dialogsMiddleware = DialogsMiddleware(getDialogsUseCase)
    }

    @Test
    fun `should handle load dialogs action`() = runBlockingTest {
        //Arrange
        val actionsInputFlow = flow<DialogsAction> {
            emit(DialogsAction.LoadDialogs)
        }
        val mockedDialogs = TestData.getMockedDialogs()
        val dialogsFlow = flow {
            emit(DialogsAction.DialogsLoaded(mockedDialogs))
        }
        whenever(getDialogsUseCase.getFlow(Unit)).thenReturn(dialogsFlow)
        //Act
        val output = mutableListOf<DialogsAction>()
        dialogsMiddleware.bind(actionsInputFlow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        //Assert
        assertTrue(output.contains(DialogsAction.DialogsLoaded(mockedDialogs)))
        verify(getDialogsUseCase).getFlow(Unit)
        verifyNoMoreInteractions(getDialogsUseCase)
    }

    @Test
    fun `should emit dialogs loading action`() = runBlockingTest {
        //Arrange
        val actionsInputFlow = flow<DialogsAction> {
            emit(DialogsAction.LoadDialogs)
        }
        val mockedDialogs = TestData.getMockedDialogs()
        val dialogsFlow = flow {
            emit(DialogsAction.DialogsLoaded(mockedDialogs))
        }
        whenever(getDialogsUseCase.getFlow(Unit)).thenReturn(dialogsFlow)
        //Act
        val output = mutableListOf<DialogsAction>()
        dialogsMiddleware.bind(actionsInputFlow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .launchIn(this)
        //Assert
        assertTrue(output.contains(DialogsAction.Loading))
        verify(getDialogsUseCase).getFlow(Unit)
        verifyNoMoreInteractions(getDialogsUseCase)
    }

    @Test
    fun `should emit unsupported actions`() = runBlockingTest {
        //Arrange
        val actionsInputFlow = flow<DialogsAction> {
            emit(DialogsAction.DialogsError(IllegalStateException()))
        }
        //Act
        val actual = dialogsMiddleware.bind(actionsInputFlow).toList()
        //Assert
        assertEquals(emptyList<DialogsAction>(), actual)
        verifyZeroInteractions(getDialogsUseCase)
    }*/

}