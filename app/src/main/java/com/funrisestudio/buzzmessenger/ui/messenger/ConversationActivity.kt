package com.funrisestudio.buzzmessenger.ui.messenger

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
import com.funrisestudio.buzzmessenger.data.contacts
import com.funrisestudio.buzzmessenger.domain.Sender
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
                    }
                )
            }
        }
    }

}

@Composable
fun ConversationScreen(
    viewStateProvider: @Composable() () -> ConversationViewState?,
    onNavigationClick: (() -> Unit)? = null
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
            viewState.sender?.let {
                ConversationToolbarContent(it)
            }
        }
    }
}

@Composable
fun ConversationToolbar(
    navigationIcon: @Composable() (() -> Unit)? = null,
    content: @Composable() () -> Unit
) {
    Surface(
        color = colorPrimary,
        elevation = paddingS,
        shape = RectangleShape
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
fun ConversationToolbarContent(sender: Sender) {
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
            asset = imageResource(sender.avatar),
            modifier = Modifier
                .tag("ivAvatar")
                .size(40.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = sender.name,
            modifier = Modifier
                .tag("tvSender")
                .padding(start = paddingM),
            style = typography.body1.copy(color = Color.White)
        )
        Text(
            text = "Online",
            modifier = Modifier
                .tag("tvIsOnline")
                .padding(start = paddingM),
            style = typography.caption.copy(color = Color.White)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessengerPreview() {
    AppTheme {
        ConversationScreen(
            viewStateProvider = {
                ConversationViewState.createSenderReceived(
                    contacts.random()
                )
            }
        )
    }
}