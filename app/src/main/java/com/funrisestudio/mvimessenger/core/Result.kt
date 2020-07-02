package com.funrisestudio.mvimessenger.core

/**
 * A generic class that holds a value or an exception
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

fun <T> Result<T>.fold(onError: (Exception) -> Any, onSuccess: (T) -> Any) {
    when (this) {
        is Result.Error -> onError(exception)
        is Result.Success -> onSuccess(data)
    }
}