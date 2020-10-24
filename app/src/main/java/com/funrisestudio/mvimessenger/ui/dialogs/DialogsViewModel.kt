package com.funrisestudio.mvimessenger.ui.dialogs

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.funrisestudio.mvimessenger.core.SingleLiveEvent
import com.funrisestudio.mvimessenger.core.mvi.Store
import com.funrisestudio.mvimessenger.domain.entity.Contact
import kotlinx.coroutines.flow.*

class DialogsViewModel @ViewModelInject constructor(
    store: Store<DialogsAction, DialogsViewState>
): ViewModel() {

    private val initialState = DialogsViewState.createEmpty()

    val viewState: StateFlow<DialogsViewState>

    private val _toMessages = SingleLiveEvent<Contact>()
    val toMessages: LiveData<Contact> = _toMessages

    init {
        store.init(viewModelScope, initialState)
        viewState = store.viewStateFlow
        store.process(DialogsAction.LoadDialogs)
    }

    fun onDialogItemSelected(item: DialogViewData) {
        _toMessages.value = item.dialog.contact
    }

}