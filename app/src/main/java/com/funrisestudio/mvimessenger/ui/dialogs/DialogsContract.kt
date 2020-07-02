package com.funrisestudio.mvimessenger.ui.dialogs

import com.funrisestudio.mvimessenger.core.mvi.Action
import com.funrisestudio.mvimessenger.core.mvi.ViewState
import com.funrisestudio.mvimessenger.domain.dialogs.Dialog

sealed class DialogsAction : Action {
    object LoadDialogs : DialogsAction()
    class DialogsLoaded(val dialogs: List<Dialog>) : DialogsAction()
    class DialogsError(val throwable: Throwable) : DialogsAction()
    object Loading : DialogsAction()
}

data class DialogsViewState(
    val items: List<DialogViewData>,
    val isLoading: Boolean,
    val hasNoDialogs: Boolean,
    val error: String
) : ViewState {

    companion object {

        fun createEmpty(): DialogsViewState {
            return DialogsViewState(
                items = emptyList(),
                isLoading = false,
                hasNoDialogs = false,
                error = ""
            )
        }

        fun createLoadingState(): DialogsViewState {
            return DialogsViewState(
                items = emptyList(),
                isLoading = true,
                hasNoDialogs = false,
                error = ""
            )
        }

        fun createDialogsReceived(dialogs: List<DialogViewData>): DialogsViewState {
            return DialogsViewState(
                items = dialogs,
                isLoading = false,
                hasNoDialogs = dialogs.isEmpty(),
                error = ""
            )
        }

        fun createErrorState(
            currentDialogs: List<DialogViewData>,
            errorMsg: String
        ): DialogsViewState {
            return DialogsViewState(
                items = currentDialogs,
                isLoading = false,
                hasNoDialogs = false,
                error = errorMsg
            )
        }

    }

}