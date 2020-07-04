package com.funrisestudio.mvimessenger.domain.messages

import com.funrisestudio.mvimessenger.domain.entity.Contact

interface MessengerRepository {

    suspend fun saveMessage(contact: Contact, message: String)

}