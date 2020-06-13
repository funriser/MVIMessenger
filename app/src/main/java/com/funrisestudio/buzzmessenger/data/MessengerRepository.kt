package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.Sender
import com.funrisestudio.buzzmessenger.core.Result

interface MessengerRepository {

    suspend fun saveMessage(sender: Sender, message: String): Result<Unit>

}