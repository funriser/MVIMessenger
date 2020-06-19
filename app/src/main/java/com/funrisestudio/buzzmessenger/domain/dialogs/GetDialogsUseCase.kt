package com.funrisestudio.buzzmessenger.domain.dialogs

import com.funrisestudio.buzzmessenger.data.dialogs.DialogsRepository
import com.funrisestudio.buzzmessenger.domain.FlowUseCase
import com.funrisestudio.buzzmessenger.ui.main.DialogsAction
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