package com.funrisestudio.mvimessenger.ui.dialogs

import android.content.Context
import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.ui.utils.ErrorHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DialogsErrorHandler @Inject constructor(
    @ApplicationContext private val context: Context
): ErrorHandler {

    override fun getErrorMessage(throwable: Throwable): String {
        return context.getString(R.string.err_unknown)
    }

}