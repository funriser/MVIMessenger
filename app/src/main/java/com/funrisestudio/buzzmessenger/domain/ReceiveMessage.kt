package com.funrisestudio.buzzmessenger.domain

import com.funrisestudio.buzzmessenger.core.Result
import com.funrisestudio.buzzmessenger.data.messages.MessengerRepository
import javax.inject.Inject

class ReceiveMessage @Inject constructor(
    private val messengerRepository: MessengerRepository
): UseCase<Unit, ReceiveMessage.Params>() {

    override suspend fun run(params: Params): Result<Unit> {
        return messengerRepository.saveMessage(params.sender, params.message)
    }

    class Params(
        val sender: Sender,
        val message: String
    )

}