package com.funrisestudio.mvimessenger.core.mvi

import android.util.Log
import com.funrisestudio.mvimessenger.core.withLatestFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface ViewState

interface Action

interface MiddleWare<A : Action, S : ViewState> {

    fun bind(actions: Flow<A>): Flow<A>

}

interface Store<A : Action, V : ViewState> {

    val viewStateFlow: StateFlow<V>

    fun init(scope: CoroutineScope, initialState: V)

    fun process(action: A)

}

@ExperimentalCoroutinesApi
class SimpleStore<A : Action, V : ViewState> @Inject constructor(
    private val reducer: Reducer<A, V>,
    private val middleWare: MiddleWare<A, V>
) : Store<A, V> {

    private lateinit var _viewStateFlow: MutableStateFlow<V>
    override val viewStateFlow: StateFlow<V>
        get() {
            return if (::_viewStateFlow.isInitialized.not()) {
                throw IllegalStateException(
                    "Call init to set up store"
                )
            } else {
                _viewStateFlow
            }
        }

    private val actionsBroadcast = BroadcastChannel<A>(Channel.BUFFERED)
    private val actionsFlow
        get() = actionsBroadcast.asFlow()

    override fun process(action: A) {
        actionsBroadcast.offer(action)
    }

    override fun init(scope: CoroutineScope, initialState: V) {
        _viewStateFlow = MutableStateFlow(initialState)
        middleWare.bind(actionsFlow)
            .onEach {
                actionsBroadcast.offer(it)
            }
            .launchIn(scope)
        actionsFlow
            .withLatestFrom(_viewStateFlow) { action: A, state: V ->
                reducer.reduce(state, action)
            }
            .distinctUntilChanged()
            .onEach { state ->
                _viewStateFlow.value = state
            }.launchIn(scope)
    }

}

interface Reducer<A : Action, S : ViewState> {
    fun reduce(viewState: S, action: A): S
}