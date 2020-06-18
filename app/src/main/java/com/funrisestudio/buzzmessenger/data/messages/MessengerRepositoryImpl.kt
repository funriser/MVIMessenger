package com.funrisestudio.buzzmessenger.data.messages

import com.funrisestudio.buzzmessenger.domain.Sender
import com.funrisestudio.buzzmessenger.core.Result
import com.funrisestudio.buzzmessenger.data.messages.local.MessengerLocalSource
import javax.inject.Inject

class MessengerRepositoryImpl @Inject constructor(
    private val messengerLocalSource: MessengerLocalSource
): MessengerRepository {

    override suspend fun saveMessage(sender: Sender, message: String): Result<Unit> {
        return messengerLocalSource.saveMessage(sender, message)
    }

}