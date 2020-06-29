package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.Notifier
import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.core.fold
import com.funrisestudio.buzzmessenger.domain.ReceiveMessage
import javax.inject.Inject

class MessengerServiceControllerImpl @Inject constructor(
    private val notifier: Notifier,
    private val interactor: ReceiveMessage
): MessengerServiceController {

    override suspend fun onNewMessage(contact: Contact, message: String) {
        val params = ReceiveMessage.Params(contact, message)
        interactor.run(params).fold(
            onError = ::onReceiveMessageError,
            onSuccess = {
                onMessageReceived(contact, message)
            }
        )
    }

    private fun onMessageReceived(contact: Contact, message: String) {
        notifier.sendMessageNotification(contact, message)
    }

    private fun onReceiveMessageError(e: Exception) {
        TODO("Handle error")
    }

}