package com.funrisestudio.buzzmessenger.data.messages

import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.core.Result

interface MessengerRepository {

    suspend fun saveMessage(contact: Contact, message: String): Result<Unit>

}