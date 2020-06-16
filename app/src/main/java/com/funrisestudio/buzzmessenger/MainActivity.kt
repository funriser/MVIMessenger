package com.funrisestudio.buzzmessenger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.IconButton
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.buzzmessenger.ui.AppTheme
import com.funrisestudio.buzzmessenger.ui.main.DialogListItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var notifier: Notifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen(items = mockedDialogs())
            }
        }
        if (savedInstanceState == null) {
            //startMessageService()
        }
    }

    fun startMessageService() {
        Intent(this, MessengerService::class.java).also {
            startService(it)
        }
    }

}

@Composable
fun MainScreen(
    items: List<DialogPreview>
) {
    Scaffold(
        topAppBar = {
            TopAppBar(
                title = { Text(text = "BuzzMessenger") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(vectorResource(R.drawable.ic_burger))
                    }
                }
            )
        }
    ) {
        MainContent(
            items = items
        )
    }
}

@Composable
fun MainContent(items: List<DialogPreview>) {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Column {
            items.forEach {
                DialogListItem(item = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(items = mockedDialogs())
    }
}

fun mockedDialogs() = listOf(
    DialogPreview(
        contact = Sender(
            id = 1,
            name = "McFly",
            avatar = R.drawable.avatar_mc_fly
        ),
        lastMessage = MessagePreview(
            text = "It's time",
            date = "15:02"
        ),
        unreadCount = 1
    ),
    DialogPreview(
        contact = Sender(
            id = 1,
            name = "Dr Brown",
            avatar = R.drawable.avatar_mc_fly
        ),
        lastMessage = MessagePreview(
            text = "It's definitely time",
            date = "15:10"
        ),
        unreadCount = 1
    )
)