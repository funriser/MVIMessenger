package com.funrisestudio.buzzmessenger.domain

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<out Type, in Params> where Type : Any {

    abstract fun getFlow(params: Params): Flow<Type>

}