package com.funrisestudio.mvimessenger.ui

import androidx.compose.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <T> observe(liveData: LiveData<T>): T? {
    val state = state { liveData.value }
    val observer = Observer<T> { state.value = it }
    onActive {
        liveData.observeForever(observer)
        onDispose {
            liveData.removeObserver(observer)
        }
    }
    return state.value
}