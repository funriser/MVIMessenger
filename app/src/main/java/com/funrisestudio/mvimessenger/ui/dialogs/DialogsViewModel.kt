package com.funrisestudio.mvimessenger.ui.dialogs

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.funrisestudio.mvimessenger.core.SingleLiveEvent
import com.funrisestudio.mvimessenger.core.mvi.Store
import com.funrisestudio.mvimessenger.domain.entity.Contact
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DialogsViewModel @ViewModelInject constructor(
    store: Store<DialogsAction, DialogsViewState>
): ViewModel() {

    private val _viewState = MutableLiveData<DialogsViewState>()
    val viewState: LiveData<DialogsViewState> = _viewState

    private val _toMessages = SingleLiveEvent<Contact>()
    val toMessages: LiveData<Contact> = _toMessages

    init {
        store.observeViewState()
            .distinctUntilChanged()
            .onEach {
                _viewState.value = it
            }
            .launchIn(viewModelScope)
        store.process(DialogsAction.LoadDialogs)
    }

    fun onDialogItemSelected(item: DialogViewData) {
        _toMessages.value = item.dialog.contact
    }

}