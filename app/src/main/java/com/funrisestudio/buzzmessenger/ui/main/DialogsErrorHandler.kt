package com.funrisestudio.buzzmessenger.ui.main

import android.content.Context
import com.funrisestudio.buzzmessenger.R
import com.funrisestudio.buzzmessenger.ui.utils.ErrorHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DialogsErrorHandler @Inject constructor(
    @ApplicationContext private val context: Context
): ErrorHandler {

    override fun getErrorMessage(throwable: Throwable): String {
        return context.getString(R.string.err_unknown)
    }

}