package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.domain.Sender

interface MessengerServiceController {

    suspend fun onNewMessage(sender: Sender, message: String)

}