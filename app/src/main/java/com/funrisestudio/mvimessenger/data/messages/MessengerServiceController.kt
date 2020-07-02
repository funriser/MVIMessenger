package com.funrisestudio.mvimessenger.data.messages

import com.funrisestudio.mvimessenger.domain.entity.Contact

interface MessengerServiceController {

    suspend fun onNewMessage(contact: Contact, message: String)

}