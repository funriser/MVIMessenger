package com.funrisestudio.mvimessenger.ui.utils

interface ErrorHandler {
    fun getErrorMessage(throwable: Throwable): String
}