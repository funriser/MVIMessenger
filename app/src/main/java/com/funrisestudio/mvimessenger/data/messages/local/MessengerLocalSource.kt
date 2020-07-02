package com.funrisestudio.mvimessenger.data.messages.local

import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.core.Result
import com.funrisestudio.mvimessenger.data.room.MessagesDao
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