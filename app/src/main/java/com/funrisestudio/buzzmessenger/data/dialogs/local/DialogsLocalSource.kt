package com.funrisestudio.buzzmessenger.data.dialogs.local

import com.funrisestudio.buzzmessenger.domain.Dialog
import com.funrisestudio.buzzmessenger.data.room.MessagesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DialogsLocalSource @Inject constructor(
    private val messengerDao: MessagesDao,
    private val dialogsMapper: DialogsMapper
) {

    fun getDialogs(): Flow<List<Dialog>> {
        return messengerDao.getDialogs()
            .map {
                dialogsMapper.getDialogs(it)
            }
    }

}