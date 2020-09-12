package com.funrisestudio.mvimessenger.ui.utils

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.setContent
import androidx.fragment.app.Fragment

fun Fragment.createComposeView(content: @Composable() () -> Unit): View {
    return FrameLayout(requireContext()).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent(Recomposer.current(), content = content)
    }
}