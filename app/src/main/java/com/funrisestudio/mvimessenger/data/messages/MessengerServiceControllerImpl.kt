package com.funrisestudio.mvimessenger.data.messages

import com.funrisestudio.mvimessenger.utils.Notifier
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.messages.MessengerRepository
import javax.inject.Inject

class MessengerServiceControllerImpl @Inject constructor(
    private val notifier: Notifier,
    private val repository: MessengerRepository
): MessengerServiceController {

    override suspend fun onNewMessage(contact: Contact, message: String) {
        repository.saveMessage(contact, message)
        notifier.sendMessageNotification(contact, message)
    }

}