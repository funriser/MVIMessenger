package com.funrisestudio.buzzmessenger.data.dialogs

import com.funrisestudio.buzzmessenger.domain.Dialog
import kotlinx.coroutines.flow.Flow

interface DialogsRepository {

    fun getDialogs(): Flow<List<Dialog>>

}