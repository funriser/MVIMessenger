package com.funrisestudio.mvimessenger.utils

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.awaitValueOrNull(
    timeMillis: Long = 2000,
    afterObserve: () -> Unit = {}
): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@awaitValueOrNull.removeObserver(this)
        }
    }
    this.observeForever(observer)
    afterObserve.invoke()
    latch.await(timeMillis, TimeUnit.MILLISECONDS)
    this.removeObserver(observer)
    return data
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.awaitValue(
    timeMillis: Long = 2000,
    afterObserve: () -> Unit = {}
): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@awaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(timeMillis, TimeUnit.MILLISECONDS)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }
    return data
}