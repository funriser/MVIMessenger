package com.funrisestudio.buzzmessenger.data.local

import com.funrisestudio.buzzmessenger.Sender
import com.funrisestudio.buzzmessenger.core.Result
import com.funrisestudio.buzzmessenger.data.room.MessagesDao
import java.lang.Exception
import javax.inject.Inject

class MessengerLocalSource @Inject constructor(
    private val messagesDao: MessagesDao,
    private val messengerMapper: MessengerMapper
) {

    suspend fun saveMessage(sender: Sender, message: String): Result<Unit> {
        val senderRow = messengerMapper.toSenderRow(sender)
        val messageRow = messengerMapper.toMessageRow(sender, message)
        return try {
            messagesDao.insertNewMessage(senderRow, messageRow)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}