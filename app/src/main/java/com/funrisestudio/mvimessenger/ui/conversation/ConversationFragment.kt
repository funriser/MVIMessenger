package com.funrisestudio.mvimessenger.ui.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.mvimessenger.R
import com.funrisestudio.mvimessenger.core.navigation.ToMessages
import com.funrisestudio.mvimessenger.core.observe
import com.funrisestudio.mvimessenger.data.contacts
import com.funrisestudio.mvimessenger.data.messages.MessengerService
import com.funrisestudio.mvimessenger.data.randomMessages
import com.funrisestudio.mvimessenger.domain.entity.Contact
import com.funrisestudio.mvimessenger.ui.*
import com.funrisestudio.mvimessenger.ui.utils.createComposeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationFragment : Fragment() {

    private val conversationViewModel: ConversationViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createComposeView {
            AppTheme {
                ConversationScreen(
                    viewStateProvider = {
                        conversationViewModel.viewState.observeAsState().value
                    },
                    onNavigationClick = {
                        findNavController().popBackStack()
                    },
                    onMessageInputChanged = {
                        conversationViewModel.onMessageInputChanged(it)
                    },
                    onSendMessageClicked = {
                        conversationViewModel.onSendMessage()
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResponseGenerator()
    }

    private fun initResponseGenerator() {
        observe(conversationViewModel.generateResponse) { senderId: Int? ->
            senderId?:return@observe
            context?.let {
                it.startService(MessengerService.getGenerateMessagesIntent(it, senderId))
            }
        }
    }

    companion object {

        fun getBundle(contact: Contact): Bundle {
            return Bundle().apply {
                putParcelable(ToMessages.KEY_CONTACT, contact)
            }
        }

    }

}

@ExperimentalFoundationApi
@Composable
fun ConversationScreen(
    viewStateProvider: @Composable() () -> ConversationViewState?,
    onNavigationClick: (() -> Unit)? = null,
    onMessageInputChanged: ((TextFieldValue) -> Unit)? = null,
    onSendMessageClicked: (() -> Unit)? = null
) {
    val viewState = viewStateProvider()?:return
    Column(modifier = Modifier.fillMaxSize()) {
        ConversationToolbar(
            navigationIcon = {
                IconButton(onClick = { onNavigationClick?.invoke() }) {
                    Icon(vectorResource(R.drawable.ic_arrow_left))
                }
            }
        ) {
            viewState.contact?.let {
                ConversationToolbarContent(it)
            }
        }
        ConversationBody(Modifier.weight(1f), viewState)
        ConversationFooter(
            viewState = viewState,
            onMessageInputChanged = onMessageInputChanged,
            onSendMessageClicked = onSendMessageClicked
        )
    }
}

@Composable
fun ConversationToolbar(
    navigationIcon: @Composable() (() -> Unit)? = null,
    content: @Composable() () -> Unit
) {
    Surface(
        color = colorPrimary,
        elevation = paddingS
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(start = paddingS, end = paddingS)
                .preferredHeight(MyAppBarHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            children = {
                if (navigationIcon != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .preferredWidth(AppBarIconWidth),
                        verticalGravity = ContentGravity.CenterVertically
                    ) {
                        navigationIcon()
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    content()
                }
            }
        )
    }
}

@Composable
fun ConversationToolbarContent(contact: Contact) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
        constraintSet = ConstraintSet {
            val ivAvatar = createRefFor("ivAvatar")
            val tvSender = createRefFor("tvSender")
            val tvIsOnline = createRefFor("tvIsOnline")

            constrain(ivAvatar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(tvSender) {
                start.linkTo(ivAvatar.end)
                top.linkTo(parent.top)
            }

            constrain(tvIsOnline) {
                start.linkTo(ivAvatar.end)
                bottom.linkTo(parent.bottom)
            }

            createVerticalChain(
                tvSender,
                tvIsOnline,
                chainStyle = ChainStyle.Packed
            )
        }
    ) {
        Image(
            asset = imageResource(contact.avatar),
            modifier = Modifier
                .layoutId("ivAvatar")
                .size(40.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = contact.name,
            modifier = Modifier
                .layoutId("tvSender")
                .padding(start = paddingL),
            style = typography.body1.copy(color = Color.White)
        )
        Text(
            text = "Online",
            modifier = Modifier
                .layoutId("tvIsOnline")
                .padding(start = paddingL),
            style = typography.caption.copy(color = Color.White)
        )
    }
}

@Composable
fun ConversationBody(
    modifier: Modifier,
    viewState: ConversationViewState
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        gravity = ContentGravity.BottomStart
    ) {
        if (viewState.messages.isNotEmpty()) {
            ScrollableColumn(
                modifier = Modifier
                    .padding(paddingXL),
                reverseScrollDirection = true
            ) {
                Column {
                    viewState.messages.forEachIndexed { i, msg ->
                        val pdTop = if (i != 0) {
                            paddingS
                        } else {
                            0.dp
                        }
                        ConversationListItem(item = msg, paddingTop = pdTop)
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ConversationFooter(
    viewState: ConversationViewState,
    onMessageInputChanged: ((TextFieldValue) -> Unit)? = null,
    onSendMessageClicked: (() -> Unit)? = null
) {
    Surface(
        elevation = elevationDefault,
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(messageBoxHeight),
            verticalGravity = Alignment.CenterVertically
        ) {
            HintTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = paddingXL),
                textValue = viewState.messageInput,
                hint = "Message",
                cursorColor = colorPrimary,
                textStyle = typography.body2.copy(color = Color.Black),
                hintStyle = typography.body2,
                onTextChanged = onMessageInputChanged
            )
            AppIconButton(
                onClick = { onSendMessageClicked?.invoke() },
                enabled = viewState.sendMessageEnabled
            ) {
                Icon(
                    asset = vectorResource(R.drawable.ic_send),
                    tint = if (viewState.sendMessageEnabled) {
                        colorPrimary
                    } else {
                        colorPrimaryLight
                    }
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun MessengerPreview() {
    AppTheme {
        ConversationScreen(
            viewStateProvider = {
                ConversationViewState.createConversationReceived(
                    contact = contacts.random(),
                    messages = randomMessages(30)
                )
            }
        )
    }
}