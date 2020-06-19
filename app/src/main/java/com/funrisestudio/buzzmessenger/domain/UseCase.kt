package com.funrisestudio.buzzmessenger.domain

import com.funrisestudio.buzzmessenger.core.Result
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    open val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    abstract suspend fun run(params: Params): Result<Type>

    operator fun invoke(
        params: Params,
        coroutineScope: CoroutineScope,
        then: (Result<Type>) -> Unit = {}
    ) {
        coroutineScope.launch {
            val res = withContext(dispatcher) {
                run(params)
            }
            then.invoke(res)
        }
    }

}
