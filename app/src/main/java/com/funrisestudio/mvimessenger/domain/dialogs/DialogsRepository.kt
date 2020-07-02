package com.funrisestudio.mvimessenger.domain.dialogs

import com.funrisestudio.mvimessenger.domain.dialogs.Dialog
import kotlinx.coroutines.flow.Flow

interface DialogsRepository {

    fun getDialogs(): Flow<List<Dialog>>

}