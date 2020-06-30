package com.funrisestudio.buzzmessenger.ui.messenger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.Composable
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.RectangleShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.funrisestudio.buzzmessenger.R
import com.funrisestudio.buzzmessenger.core.navigation.ToMessages
import com.funrisestudio.buzzmessenger.data.contacts
import com.funrisestudio.buzzmessenger.data.randomMessages
import com.funrisestudio.buzzmessenger.domain.Contact
import com.funrisestudio.buzzmessenger.ui.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationActivity : AppCompatActivity() {

    private val conversationViewModel: ConversationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ConversationScreen(
                    viewStateProvider = {
                        observe(liveData = conversationViewModel.viewState)
                    },
                    onNavigationClick = {
                        onBackPressed()
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

    companion object {

        fun getIntent(context: Context, contact: Contact): Intent {
            return Intent(context, ConversationActivity::class.java).apply {
                putExtra(ToMessages.KEY_CONTACT, contact)
            }
        }

    }

}

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
            val ivAvatar = tag("ivAvatar").apply {
                left constrainTo parent.left
                top constrainTo parent.top
                bottom constrainTo parent.bottom
            }
            val tvSender = tag("tvSender").apply {
                left constrainTo ivAvatar.right
                top constrainTo parent.top
            }
            val tvIsOnline = tag("tvIsOnline").apply {
                left constrainTo ivAvatar.right
                bottom constrainTo parent.bottom
            }
            createVerticalChain(
                tvSender,
                tvIsOnline,
                chainStyle = ConstraintSetBuilderScope.ChainStyle.Packed
            )
        }
    ) {
        Image(
            asset = imageResource(contact.avatar),
            modifier = Modifier
                .tag("ivAvatar")
                .size(40.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = contact.name,
            modifier = Modifier
                .tag("tvSender")
                .padding(start = paddingL),
            style = typography.body1.copy(color = Color.White)
        )
        Text(
            text = "Online",
            modifier = Modifier
                .tag("tvIsOnline")
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
            VerticalScroller(
                modifier = Modifier
                    .padding(paddingXL),
                scrollerPosition = ScrollerPosition(isReversed = true)
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