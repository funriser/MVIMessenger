package com.funrisestudio.mvimessenger.data.messages

import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.core.Result
import com.funrisestudio.mvimessenger.data.messages.local.MessengerLocalSource
import com.funrisestudio.mvimessenger.domain.messages.MessengerRepository
import javax.inject.Inject

class MessengerRepositoryImpl @Inject constructor(
    private val messengerLocalSource: MessengerLocalSource
): MessengerRepository {

    override suspend fun saveMessage(contact: Contact, message: String): Result<Unit> {
        return messengerLocalSource.saveMessage(contact, message)
    }

}