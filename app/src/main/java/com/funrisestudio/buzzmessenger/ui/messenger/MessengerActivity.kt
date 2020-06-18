package com.funrisestudio.buzzmessenger.ui.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.tooling.preview.Preview
import com.funrisestudio.buzzmessenger.ui.AppTheme

class MessengerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MessengerScreen()
            }
        }
    }

}

@Composable
fun MessengerScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        gravity = ContentGravity.Center
    ) {
        Text(text = "Messenger will be there")
    }
}

@Preview(showBackground = true)
@Composable
fun MessengerPreview() {
    AppTheme {
        MessengerScreen()
    }
}