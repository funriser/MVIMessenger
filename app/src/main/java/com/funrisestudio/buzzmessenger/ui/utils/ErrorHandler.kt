package com.funrisestudio.buzzmessenger.ui.utils

interface ErrorHandler {
    fun getErrorMessage(throwable: Throwable): String
}