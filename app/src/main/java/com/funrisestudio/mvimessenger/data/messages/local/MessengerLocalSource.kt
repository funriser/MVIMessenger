package com.funrisestudio.mvimessenger.data.messages.local

import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.data.room.MessagesDao
import javax.inject.Inject

class MessengerLocalSource @Inject constructor(
    private val messagesDao: MessagesDao,
    private val messengerMapper: MessengerMapper
) {

    suspend fun saveMessage(contact: Contact, message: String) {
        val contactRow = messengerMapper.toContactRow(contact)
        val messageRow = messengerMapper.toMessageRow(contact, message)
        messagesDao.insertNewMessage(contactRow, messageRow)
    }

}