package com.funrisestudio.buzzmessenger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.Button
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.buzzmessenger.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var notifier: Notifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen(notifier = notifier)
            }
        }
        if (savedInstanceState == null) {
            Intent(this, MessengerService::class.java).also {
                startService(it)
            }
        }
    }

}

@Composable
fun MainScreen(notifier: Notifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        gravity = ContentGravity.Center
    ) {
        Button(
            onClick = {
                notifier.sendMessageNotification(Sender.macFly(), "It's time")
            }
        ) {
            Text(text = "Send notification")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        MainScreen(notifier = StubNotifier())
    }
}