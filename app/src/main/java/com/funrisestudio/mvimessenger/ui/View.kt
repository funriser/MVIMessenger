package com.funrisestudio.mvimessenger.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Snackbar
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.funrisestudio.mvimessenger.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ErrorSnackbar(
    showError: Boolean,
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    onErrorAction: () -> Unit = { },
    onDismiss: () -> Unit = { }
) {
    if (showError) {
        // Make Snackbar disappear after 5 seconds if the user hasn't interacted with it
        onActive {
            val scope = MainScope()
            scope.launch {
                delay(5000L)
                onDismiss()
            }
            onDispose {
                scope.cancel()
            }
        }

        Snackbar(
            modifier = modifier.padding(16.dp),
            text = { Text(errorMessage) },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                        onDismiss()
                    },
                    contentColor = contentColor()
                ) {
                    Text(
                        text = ContextAmbient.current.getString(R.string.err_retry),
                        color = colorAccent
                    )
                }
            }
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun HintTextField(
    modifier: Modifier,
    textValue: TextFieldValue,
    hint: String,
    onTextChanged: ((TextFieldValue) -> Unit)? = null,
    cursorColor: Color = contentColor(),
    textStyle: TextStyle = typography.body2,
    hintStyle: TextStyle = typography.body2
) {
    Stack(
        modifier = modifier
    ) {
        BaseTextField(
            value = textValue,
            onValueChange = { onTextChanged?.invoke(it) },
            textStyle = textStyle,
            cursorColor = cursorColor
        )
        if (textValue.text.isEmpty()) {
            Text(
                text = hint,
                style = hintStyle
            )
        }
    }
}

@Composable
fun AppIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable() () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = RippleIndication(bounded = false, radius = RippleRadius),
                enabled = enabled
            )
            .plus(IconButtonSizeModifier),
        gravity = ContentGravity.Center,
        children = icon
    )
}

private val RippleRadius = 24.dp
private val IconButtonSizeModifier = Modifier.preferredSize(48.dp)