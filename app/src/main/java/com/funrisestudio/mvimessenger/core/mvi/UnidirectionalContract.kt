package com.funrisestudio.mvimessenger.core.mvi

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

    var onEachAction: ((A) -> Unit)?

    fun process(action: A)

    fun observeViewState(): Flow<V>

}

@ExperimentalCoroutinesApi
class SimpleStore<A : Action, V : ViewState> @Inject constructor(
    private val reducer: Reducer<A, V>,
    middleWare: MiddleWare<A, V>,
    private val initialState: V
) : Store<A, V> {

    private val actionsBroadcast = BroadcastChannel<A>(Channel.BUFFERED)
    private val actionsFlow
        get() = actionsBroadcast.asFlow()
    private val middlewareActionsFlow = middleWare.bind(actionsFlow)

    override var onEachAction: ((A) -> Unit)? = null

    override fun process(action: A) {
        actionsBroadcast.offer(action)
    }

    override fun observeViewState(): Flow<V> {
        return merge(actionsFlow, middlewareActionsFlow)
            .onEach {
                onEachAction?.invoke(it)
            }
            .scan(initialState) { acc, value ->
                reducer.reduce(acc, value)
            }
    }

}

interface Reducer<A : Action, S : ViewState> {
    fun reduce(viewState: S, action: A): S
}