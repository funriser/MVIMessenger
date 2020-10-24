package com.funrisestudio.mvimessenger.ui.utils

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

fun Fragment.createComposeView(content: @Composable() () -> Unit): View {
    return ComposeView(requireContext()).apply {
        setContent {
            content()
        }
    }
}