package com.funrisestudio.buzzmessenger.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.funrisestudio.buzzmessenger.domain.Dialog
import com.funrisestudio.buzzmessenger.data.dialogs.DialogsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val dialogsRepository: DialogsRepository,
    private val mapper: DialogViewDataMapper
): ViewModel() {

    private val _dialogs = MutableLiveData<List<Dialog>>()
    val dialogs: LiveData<List<DialogViewData>> = _dialogs.map {
        mapper.getDialogViewDataList(it)
    }

    init {
        getDialogs()
    }

    private fun getDialogs() {
        viewModelScope.launch {
            dialogsRepository.getDialogs().collect {
                _dialogs.value = it
            }
        }
    }

}