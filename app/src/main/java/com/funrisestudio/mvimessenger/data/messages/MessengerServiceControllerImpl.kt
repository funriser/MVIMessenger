package com.funrisestudio.mvimessenger.data.messages

import com.funrisestudio.mvimessenger.utils.Notifier
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.messages.ReceiveMessageUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MessengerServiceControllerImpl @Inject constructor(
    private val notifier: Notifier,
    private val interactor: ReceiveMessageUseCase
): MessengerServiceController {

    override suspend fun onNewMessage(contact: Contact, message: String) {
        val params = ReceiveMessageUseCase.Params(contact, message)
        interactor.getFlow(params)
            .catch {
                onReceiveMessageError(it)
            }
            .collect {
                onMessageReceived(contact, message)
            }
    }

    private fun onMessageReceived(contact: Contact, message: String) {
        notifier.sendMessageNotification(contact, message)
    }

    private fun onReceiveMessageError(e: Throwable) {
        TODO("Handle error")
    }

}