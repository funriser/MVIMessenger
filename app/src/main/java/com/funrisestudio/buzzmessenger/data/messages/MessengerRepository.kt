package com.funrisestudio.buzzmessenger.data.messages

import com.funrisestudio.buzzmessenger.domain.Sender
import com.funrisestudio.buzzmessenger.core.Result

interface MessengerRepository {

    suspend fun saveMessage(sender: Sender, message: String): Result<Unit>

}