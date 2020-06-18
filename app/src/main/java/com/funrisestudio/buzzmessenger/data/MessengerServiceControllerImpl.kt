package com.funrisestudio.buzzmessenger.data

import com.funrisestudio.buzzmessenger.Notifier
import com.funrisestudio.buzzmessenger.domain.Sender
import com.funrisestudio.buzzmessenger.core.fold
import com.funrisestudio.buzzmessenger.domain.ReceiveMessage
import javax.inject.Inject

class MessengerServiceControllerImpl @Inject constructor(
    private val notifier: Notifier,
    private val interactor: ReceiveMessage
): MessengerServiceController {

    override suspend fun onNewMessage(sender: Sender, message: String) {
        val params = ReceiveMessage.Params(sender, message)
        interactor.run(params).fold(
            onError = ::onReceiveMessageError,
            onSuccess = {
                onMessageReceived(sender, message)
            }
        )
    }

    private fun onMessageReceived(sender: Sender, message: String) {
        notifier.sendMessageNotification(sender, message)
    }

    private fun onReceiveMessageError(e: Exception) {
        TODO("Handle error")
    }

}