package com.funrisestudio.buzzmessenger.data.dialogs

import com.funrisestudio.buzzmessenger.domain.Dialog
import com.funrisestudio.buzzmessenger.data.dialogs.local.DialogsLocalSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DialogsRepositoryImpl @Inject constructor(
    private val dialogsLocalSource: DialogsLocalSource
): DialogsRepository {

    override fun getDialogs(): Flow<List<Dialog>> {
        return dialogsLocalSource.getDialogs()
    }

}