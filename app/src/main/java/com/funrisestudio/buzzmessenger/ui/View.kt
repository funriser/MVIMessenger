package com.funrisestudio.buzzmessenger.ui

import androidx.compose.Composable
import androidx.compose.onActive
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.layout.padding
import androidx.ui.material.Snackbar
import androidx.ui.material.TextButton
import androidx.ui.unit.dp
import com.funrisestudio.buzzmessenger.R
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