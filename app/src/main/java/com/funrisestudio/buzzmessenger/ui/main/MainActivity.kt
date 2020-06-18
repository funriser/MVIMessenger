package com.funrisestudio.buzzmessenger.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
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
import com.funrisestudio.buzzmessenger.data.messages.MessengerService
import com.funrisestudio.buzzmessenger.R
import com.funrisestudio.buzzmessenger.ui.AppTheme
import com.funrisestudio.buzzmessenger.ui.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen {
                    observe(liveData = mainViewModel.dialogs)
                }
            }
        }
        if (savedInstanceState == null) {
            startMessageService()
        }
    }

    private fun startMessageService() {
        Intent(this, MessengerService::class.java).also {
            startService(it)
        }
    }

}

@Composable
fun MainScreen(dialogsProvider: @Composable() () -> List<DialogViewData>?) {
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
        MainContent(dialogsProvider)
    }
}

@Composable
fun MainContent(dialogsProvider: @Composable() () -> List<DialogViewData>?) {
    dialogsProvider()?.let { dialogs ->
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            Column {
                dialogs.forEach {
                    DialogListItem(item = it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen {
            getFakeDialogViewData()
        }
    }
}