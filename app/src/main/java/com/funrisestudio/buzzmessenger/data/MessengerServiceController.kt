package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.domain.Contact

interface MessengerServiceController {

    suspend fun onNewMessage(contact: Contact, message: String)

}