package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.core.mvi.MiddleWare
import com.funrisestudio.mvimessenger.core.ofType
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class DialogsMiddleware @Inject constructor(
    private val dialogsRepository: DialogsRepository
): MiddleWare<DialogsAction, DialogsViewState> {

    override fun bind(actions: Flow<DialogsAction>) =
        loadDialogsFlow(actions.ofType()).flowOn(Dispatchers.IO)

    private fun loadDialogsFlow(
        actions: Flow<DialogsAction.LoadDialogs>
    ) = actions.flatMapLatest {
        dialogsRepository.getDialogs()
            .map { DialogsAction.DialogsLoaded(it) }
    }

}