package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@FlowPreview
class DialogsMiddlewareTest {

    private val dialogsRepository: DialogsRepository = mock()
    private lateinit var dialogsMiddleware: DialogsMiddleware

    @Before
    fun setUp() {
        dialogsMiddleware = DialogsMiddleware(dialogsRepository)
    }

    @Test
    fun `should handle load dialogs action`() = runBlocking<Unit> {
        //Arrange
        val actionsInputFlow = flow<DialogsAction> {
            emit(DialogsAction.LoadDialogs)
        }
        val mockedDialogs = TestData.getMockedDialogs()
        val dialogsFlow = flow {
            emit(mockedDialogs)
        }
        whenever(dialogsRepository.getDialogs()).thenReturn(dialogsFlow)
        //Act
        val output = mutableListOf<DialogsAction>()
        dialogsMiddleware.bind(actionsInputFlow)
            .take(2)
            .onEach {
                output.add(it)
            }
            .toList()
        //Assert
        assertTrue(output.contains(DialogsAction.DialogsLoaded(mockedDialogs)))
        verify(dialogsRepository).getDialogs()
    }

    @Test
    fun `should not emit unsupported actions`() = runBlocking {
        //Arrange
        val actionsInputFlow = flow<DialogsAction> {
            emit(DialogsAction.DialogsError(IllegalStateException()))
        }
        //Act
        val actual = dialogsMiddleware.bind(actionsInputFlow).toList()
        //Assert
        assertEquals(emptyList<DialogsAction>(), actual)
    }

}