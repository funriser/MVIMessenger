package com.funrisestudio.buzzmessenger.data.messages

import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.core.Result
import com.funrisestudio.buzzmessenger.data.messages.local.MessengerLocalSource
import javax.inject.Inject

class MessengerRepositoryImpl @Inject constructor(
    private val messengerLocalSource: MessengerLocalSource
): MessengerRepository {

    override suspend fun saveMessage(contact: Contact, message: String): Result<Unit> {
        return messengerLocalSource.saveMessage(contact, message)
    }

}