package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.FlowUseCase
import com.funrisestudio.mvimessenger.ui.dialogs.DialogsAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDialogsUseCase @Inject constructor(
    private val dialogsRepository: DialogsRepository
): FlowUseCase<DialogsAction, Unit>() {

    override fun getFlow(params: Unit): Flow<DialogsAction> {
        return dialogsRepository.getDialogs()
            .map { DialogsAction.DialogsLoaded(it) }
    }

}