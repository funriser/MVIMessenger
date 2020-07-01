package com.funrisestudio.buzzmessenger.core.mvi

import com.funrisestudio.buzzmessenger.core.withLatestFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface ViewState

interface Action

abstract class MiddleWare<A : Action, V : ViewState> {
    abstract fun bind(actionStream: Flow<A>): Flow<A>
}

interface Store<A : Action, V : ViewState> {

    /**
     * bind all actions with middleware
     */
    fun wire(scope: CoroutineScope)

    fun processAction(action: A)

    fun observeViewState(): Flow<V>

    fun interceptActions(): Flow<A>
}

@FlowPreview
@ExperimentalCoroutinesApi
class DefaultStore<A : Action, V : ViewState> @Inject constructor(
    private val reducer: Reducer<A, V>,
    private val middleWare: MiddleWare<A, V>,
    initialState: V
) : Store<A, V> {

    private val allActions = BroadcastChannel<A>(Channel.BUFFERED)
    private val states = ConflatedBroadcastChannel(initialState)

    override fun wire(scope: CoroutineScope) {
        middleWare.bind(allActions.asFlow())
            .onEach {
                allActions.offer(it)
            }
            .launchIn(scope)
        allActions.asFlow()
            .withLatestFrom(states.asFlow()) { action, states ->
                reducer.reduce(states, action)
            }
            .onEach {
                states.offer(it)
            }
            .launchIn(scope)
    }

    override fun processAction(action: A) {
        allActions.offer(action)
    }

    override fun observeViewState(): Flow<V> = states.asFlow().buffer()

    override fun interceptActions(): Flow<A> = allActions.asFlow().buffer()

}

@FlowPreview
@ExperimentalCoroutinesApi
class PresentationStore<A : Action, V : ViewState> @Inject constructor(
    private val reducer: Reducer<A, V>,
    initialState: V
) : Store<A, V> {

    private val allActions = BroadcastChannel<A>(Channel.BUFFERED)
    private val states = ConflatedBroadcastChannel(initialState)

    override fun wire(scope: CoroutineScope) {
        allActions.asFlow()
            .withLatestFrom(states.asFlow()) { action, states ->
                reducer.reduce(states, action)
            }
            .onEach {
                states.offer(it)
            }
            .launchIn(scope)
    }

    override fun processAction(action: A) {
        allActions.offer(action)
    }

    override fun observeViewState(): Flow<V> = states.asFlow().buffer()

    override fun interceptActions(): Flow<A> = allActions.asFlow().buffer()

}

interface Reducer<A : Action, V : ViewState> {
    fun reduce(viewState: V, action: A): V
}