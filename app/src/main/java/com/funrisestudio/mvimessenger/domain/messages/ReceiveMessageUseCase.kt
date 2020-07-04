package com.funrisestudio.mvimessenger.domain.messages

import com.funrisestudio.mvimessenger.domain.FlowUseCase
import com.funrisestudio.mvimessenger.domain.entity.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReceiveMessageUseCase @Inject constructor(
    private val messengerRepository: MessengerRepository
): FlowUseCase<Unit, ReceiveMessageUseCase.Params>() {

    override fun getFlow(params: Params): Flow<Unit> {
        return flow {
            messengerRepository.saveMessage(params.contact, params.message)
            emit(Unit)
        }
    }

    class Params(
        val contact: Contact,
        val message: String
    )

}