package com.funrisestudio.mvimessenger.ui.dialogs

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.stateFor
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.layout.Column
import androidx.ui.layout.Stack
import androidx.ui.layout.fillMaxSize
import androidx.ui.livedata.observeAsState
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.IconButton
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.mvimessenger.data.messages.MessengerService
import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.core.navigation.NavAction
import com.funrisestudio.mvimessenger.core.navigation.Navigator
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.core.observe
import com.funrisestudio.mvimessenger.ui.AppTheme
import com.funrisestudio.mvimessenger.ui.ErrorSnackbar
import com.funrisestudio.mvimessenger.ui.typography
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogsActivity : AppCompatActivity() {

    private val dialogsViewModel: DialogsViewModel by viewModels()
    @Inject lateinit var navigator: Navigator<NavAction.DialogNavAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                DialogsScreen(
                    viewStateProvider = {
                        dialogsViewModel.viewState.observeAsState().value
                    },
                    onDialogItemSelected ={
                        dialogsViewModel.onDialogItemSelected(it)
                    }
                )
            }
        }
        initNavigation()
        initMessaging(savedInstanceState == null)
    }

    private fun initNavigation() {
        observe(dialogsViewModel.toMessages) { sender ->
            sender?:return@observe
            navigator.handleAction(this, ToMessages(sender))
        }
    }

    private fun initMessaging(isFirstStarted: Boolean) {
        if (isFirstStarted) {
            startService(MessengerService.getGenerateMessagesIntent(this))
        }
    }

}

typealias OnDialogItemSelected = (DialogViewData) -> Unit

@Composable
fun DialogsScreen(
    viewStateProvider: @Composable() () -> DialogsViewState?,
    onDialogItemSelected: OnDialogItemSelected
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Messenger") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(vectorResource(R.drawable.ic_burger))
                    }
                }
            )
        }
    ) {
        DialogsContent(viewStateProvider, onDialogItemSelected)
    }
}

@Composable
fun DialogsContent(
    viewStateProvider: @Composable() () -> DialogsViewState?,
    onDialogItemSelected: OnDialogItemSelected
) {
    val viewState = viewStateProvider()?:return
    if (viewState.hasNoDialogs || viewState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            gravity = ContentGravity.Center
        ) {
            if (viewState.isLoading) {
                CircularProgressIndicator()
            }
            if (viewState.hasNoDialogs) {
                Text(
                    text = "Waiting for someone to write you...",
                    style = typography.body2
                )
            }
        }
    } else {
        DialogsScreenBodyWrapper(
            state = viewState,
            onErrorAction = {},
            onDialogItemSelected = onDialogItemSelected
        )
    }
}

@Composable
fun DialogsScreenBodyWrapper(
    modifier: Modifier = Modifier,
    state: DialogsViewState,
    onErrorAction: () -> Unit,
    onDialogItemSelected: OnDialogItemSelected
) {
    // State for showing the Snackbar error. This state will reset with the content of the lambda
    // inside stateFor each time the viewState input parameter changes.
    // showSnackbarError is the value of the error state, use updateShowSnackbarError to update it.
    val (showSnackbarError, updateShowSnackbarError) = stateFor(state) {
        state.error.isNotEmpty()
    }

    Stack(modifier = modifier.fillMaxSize()) {
        if (state.items.isNotEmpty()) {
            DialogsScreenBody(state.items, onDialogItemSelected)
        }
        ErrorSnackbar(
            showError = showSnackbarError,
            onErrorAction = onErrorAction,
            errorMessage = state.error,
            onDismiss = { updateShowSnackbarError(false) },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DialogsScreenBody(
    items: List<DialogViewData>,
    onDialogItemSelected: OnDialogItemSelected
) {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Column {
            items.forEach {
                DialogListItem(item = it, onClick = onDialogItemSelected)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogsScreenPreview() {
    AppTheme {
        DialogsScreen(
            viewStateProvider = {
                DialogsViewState.createDialogsReceived(
                    getFakeDialogViewData()
                )
            },
            onDialogItemSelected = {

            }
        )
    }
}