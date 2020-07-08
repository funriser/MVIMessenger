package com.funrisestudio.mvimessenger.ui.dialogs

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.funrisestudio.mvimessenger.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DialogsErrorHandlerTest {

    private val appCtx: Context = ApplicationProvider.getApplicationContext()

    private lateinit var dialogsErrorHandler: DialogsErrorHandler

    @Before
    fun setUp() {
        dialogsErrorHandler = DialogsErrorHandler(appCtx)
    }

    @Test
    fun `should get error message for exception`() {
        val testException = IllegalStateException()
        val expectedMsg = appCtx.getString(R.string.err_unknown)
        assertEquals(expectedMsg, dialogsErrorHandler.getErrorMessage(testException))
    }

}