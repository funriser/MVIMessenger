package com.funrisestudio.buzzmessenger.domain.dialogs

import com.funrisestudio.buzzmessenger.core.mvi.MiddleWare
import com.funrisestudio.buzzmessenger.ui.main.DialogsAction
import com.funrisestudio.buzzmessenger.ui.main.DialogsViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class DialogsMiddleware @Inject constructor(
    private val getDialogsUseCase: GetDialogsUseCase
): MiddleWare<DialogsAction, DialogsViewState>() {

    override fun bind(actionStream: Flow<DialogsAction>): Flow<DialogsAction> {
        return actionStream
            .filter {
                isHandled(it)
            }
            .flatMapMerge {
                when(it) {
                    is DialogsAction.LoadDialogs -> {
                        getDialogsUseCase.getFlow(Unit)
                            .onStart { emit(DialogsAction.Loading) }
                    }
                    else -> throw IllegalStateException("Action is not supported")
                }
            }
    }

    private fun isHandled(action: DialogsAction): Boolean {
        return action is DialogsAction.LoadDialogs
    }

}