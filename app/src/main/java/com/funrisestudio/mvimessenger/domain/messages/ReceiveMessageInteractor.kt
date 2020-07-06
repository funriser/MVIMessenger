package com.funrisestudio.mvimessenger.domain.messages

import com.funrisestudio.mvimessenger.domain.FlowUseCase
import com.funrisestudio.mvimessenger.domain.entity.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ReceiveMessageUseCase : FlowUseCase<Unit, ReceiveMessageUseCase.Params> {

    class Params(
        val contact: Contact,
        val message: String
    )

}

class ReceiveMessageInteractor @Inject constructor(
    private val messengerRepository: MessengerRepository
) : ReceiveMessageUseCase {

    override fun getFlow(params: ReceiveMessageUseCase.Params): Flow<Unit> {
        return flow {
            messengerRepository.saveMessage(params.contact, params.message)
            emit(Unit)
        }
    }

}