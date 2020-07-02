package com.funrisestudio.mvimessenger.ui

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.Stack
import androidx.ui.layout.padding
import androidx.ui.layout.preferredSize
import androidx.ui.material.Snackbar
import androidx.ui.material.TextButton
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import com.funrisestudio.mvimessenger.R
import kotlinx.coroutines.*

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
        TextField(
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