package com.funrisestudio.mvimessenger.data.dialogs

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import com.funrisestudio.mvimessenger.data.dialogs.local.DialogsLocalSource
import com.funrisestudio.mvimessenger.domain.dialogs.DialogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DialogsRepositoryImpl @Inject constructor(
    private val dialogsLocalSource: DialogsLocalSource
): DialogsRepository {

    override fun getDialogs(): Flow<List<Dialog>> {
        return dialogsLocalSource.getDialogs()
    }

}