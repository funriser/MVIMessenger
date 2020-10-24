package com.funrisestudio.mvimessenger.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference

fun <A, B: Any, R> Flow<A>.withLatestFrom(other: Flow<B>, transform: suspend (A, B) -> R): Flow<R> = flow {
    coroutineScope {
        val latestB = AtomicReference<B?>()
        val outerScope = this
        launch {
            try {
                other.collect {
                    latestB.set(it)
                }
            } catch(e: CancellationException) {
                outerScope.cancel(e) // cancel outer scope on cancellation exception, too
            }
        }
        collect { a: A ->
            latestB.get()?.let { b -> emit(transform(a, b)) }
        }
    }
}

inline fun <reified T> Flow<Any>.ofType(): Flow<T> = mapNotNull { it as? T }

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

fun <T : Any, L : LiveData<T>> AppCompatActivity.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}