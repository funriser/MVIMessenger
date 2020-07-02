package com.funrisestudio.mvimessenger.domain.messages

import com.funrisestudio.mvimessenger.core.Result
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.domain.UseCase
import javax.inject.Inject

class ReceiveMessage @Inject constructor(
    private val messengerRepository: MessengerRepository
): UseCase<Unit, ReceiveMessage.Params>() {

    override suspend fun run(params: Params): Result<Unit> {
        return messengerRepository.saveMessage(params.contact, params.message)
    }

    class Params(
        val contact: Contact,
        val message: String
    )

}