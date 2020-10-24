package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.core.mvi.MiddleWare
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
    private val getDialogsUseCase: GetDialogsUseCase
): MiddleWare<DialogsAction, DialogsViewState> {

    override fun bind(actions: Flow<DialogsAction>): Flow<DialogsAction> {
        return actions
            .flatMapMerge {
                when(it) {
                    is DialogsAction.LoadDialogs -> {
                        getDialogsUseCase.getFlow(Unit)
                            .onStart { emit(DialogsAction.Loading) }
                    }
                    else -> emptyFlow()
                }
            }
            .flowOn(Dispatchers.IO)
    }

}