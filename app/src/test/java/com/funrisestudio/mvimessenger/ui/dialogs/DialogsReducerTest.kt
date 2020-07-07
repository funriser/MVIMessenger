package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.utils.ErrorHandler
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DialogsReducerTest {

    private val dialogViewDataMapper: DialogViewDataMapper = mock()
    private val errorHandler: ErrorHandler = mock()

    private lateinit var dialogsReducer: DialogsReducer

    @Before
    fun setUp() {
        dialogsReducer = DialogsReducer(dialogViewDataMapper, errorHandler)
    }

    @Test
    fun `should reduce dialogs loaded action`() {
        val mockedDialogs = TestData.getMockedDialogs()
        val mockedDialogsViewData = listOf(TestData.getDialogViewData())
        val mockedState = DialogsViewState.createEmpty()
        val testAction = DialogsAction.DialogsLoaded(mockedDialogs)

        whenever(dialogViewDataMapper.getDialogViewDataList(mockedDialogs))
            .thenReturn(mockedDialogsViewData)

        val actualResult = dialogsReducer.reduce(mockedState, testAction)

        val expected = DialogsViewState(
            items = mockedDialogsViewData,
            isLoading = false,
            hasNoDialogs = false,
            error = ""
        )

        assertEquals(expected, actualResult)
    }

    @Test
    fun `should reduce dialogs loading action`() {
        val mockedState = DialogsViewState.createEmpty()
        val testAction = DialogsAction.Loading

        val actualResult = dialogsReducer.reduce(mockedState, testAction)

        val expected = DialogsViewState(
            items = listOf(),
            isLoading = true,
            hasNoDialogs = false,
            error = ""
        )

        assertEquals(expected, actualResult)
    }

    @Test
    fun `should reduce dialogs error action`() {
        val mockedState = DialogsViewState.createEmpty()
        val mockedException = IllegalStateException()
        val mockedErrorMsg = "Some error message"
        val testAction = DialogsAction.DialogsError(mockedException)

        whenever(errorHandler.getErrorMessage(mockedException))
            .thenReturn(mockedErrorMsg)

        val actualResult = dialogsReducer.reduce(mockedState, testAction)

        val expected = DialogsViewState(
            items = listOf(),
            isLoading = false,
            hasNoDialogs = false,
            error = mockedErrorMsg
        )

        assertEquals(expected, actualResult)
    }

    @Test
    fun `should ignore not important actions`() {
        val mockedState = DialogsViewState.createEmpty()
        val testAction = DialogsAction.LoadDialogs

        val actualResult = dialogsReducer.reduce(mockedState, testAction)

        //view state not changed
        assertEquals(mockedState, actualResult)
    }

}