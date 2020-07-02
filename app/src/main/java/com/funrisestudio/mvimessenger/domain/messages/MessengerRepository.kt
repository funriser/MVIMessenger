package com.funrisestudio.mvimessenger.domain.messages

import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.core.Result

interface MessengerRepository {

    suspend fun saveMessage(contact: Contact, message: String): Result<Unit>

}