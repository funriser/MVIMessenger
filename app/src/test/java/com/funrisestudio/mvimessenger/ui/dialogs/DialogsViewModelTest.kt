package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.core.mvi.Store
import com.funrisestudio.mvimessenger.domain.TestData
import com.funrisestudio.mvimessenger.ui.ViewModelTest
import com.funrisestudio.mvimessenger.ui.conversation.ConversationAction
import com.funrisestudio.mvimessenger.ui.conversation.ConversationViewModel
import com.funrisestudio.mvimessenger.utils.awaitValue
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DialogsViewModelTest: ViewModelTest() {

    private val store: Store<DialogsAction, DialogsViewState> = mock()

    private lateinit var viewModel: DialogsViewModel

    @Test
    fun `should init store relationship and launch loading`() {
        viewModel = DialogsViewModel(store)
        assertEquals(store.viewStateFlow, viewModel.viewState)
        verify(store).process(DialogsAction.LoadDialogs)
    }

    @Test
    fun `should update live data after dialog item selected`() {
        val testDialogViewData = TestData.getDialogViewData()
        val initialState = DialogsViewState.createEmpty()
        val mockedStateFlow = MutableStateFlow(initialState)
        whenever(store.viewStateFlow).thenReturn(mockedStateFlow)

        viewModel = DialogsViewModel(store)

        viewModel.onDialogItemSelected(testDialogViewData)

        val actualContact = viewModel.toMessages.awaitValue()

        assertEquals(testDialogViewData.dialog.contact, actualContact)
    }

}