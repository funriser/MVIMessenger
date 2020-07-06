package com.funrisestudio.mvimessenger.domain

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<out Type, in Params> where Type : Any {

    fun getFlow(params: Params): Flow<Type>

}