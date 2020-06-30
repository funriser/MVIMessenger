package com.funrisestudio.buzzmessenger.data.messages.local

import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.core.Result
import com.funrisestudio.buzzmessenger.data.room.MessagesDao
import java.lang.Exception
import javax.inject.Inject

class MessengerLocalSource @Inject constructor(
    private val messagesDao: MessagesDao,
    private val messengerMapper: MessengerMapper
) {

    suspend fun saveMessage(contact: Contact, message: String): Result<Unit> {
        val contactRow = messengerMapper.toContactRow(contact)
        val messageRow = messengerMapper.toMessageRow(contact, message)
        return try {
            messagesDao.insertNewMessage(contactRow, messageRow)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}