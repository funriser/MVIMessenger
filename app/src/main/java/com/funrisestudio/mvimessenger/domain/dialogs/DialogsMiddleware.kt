package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.core.mvi.MiddleWare
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class DialogsMiddleware @Inject constructor(
    private val getDialogsUseCase: GetDialogsUseCase
): MiddleWare<DialogsAction, DialogsViewState> {

    private val allActions = BroadcastChannel<DialogsAction>(Channel.BUFFERED)

    override fun process(action: DialogsAction) {
        allActions.offer(action)
    }

    override fun getProcessedActions(): Flow<DialogsAction> {
        return allActions.asFlow()
            .flatMapMerge {
                when(it) {
                    is DialogsAction.LoadDialogs -> {
                        getDialogsUseCase.getFlow(Unit)
                            .onStart { emit(DialogsAction.Loading) }
                    }
                    else -> flow { emit(it) }
                }
            }
    }

}