package com.funrisestudio.mvimessenger.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.stateFor
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.core.navigation.NavAction
import com.funrisestudio.mvimessenger.core.navigation.Navigator
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.core.observe
import com.funrisestudio.mvimessenger.ui.AppTheme
import com.funrisestudio.mvimessenger.ui.ErrorSnackbar
import com.funrisestudio.mvimessenger.ui.typography
import com.funrisestudio.mvimessenger.ui.utils.createComposeView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogsFragment : Fragment() {

    private val dialogsViewModel: DialogsViewModel by viewModels()
    @Inject lateinit var navigator: Navigator<NavAction.DialogNavAction>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createComposeView {
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        observe(dialogsViewModel.toMessages) { sender ->
            sender?:return@observe
            navigator.handleAction(findNavController(), ToMessages(sender))
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
    ScrollableColumn(modifier = Modifier.fillMaxSize()) {
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