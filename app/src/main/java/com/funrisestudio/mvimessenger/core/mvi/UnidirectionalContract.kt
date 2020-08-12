package com.funrisestudio.mvimessenger.core.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface ViewState

interface Action

interface MiddleWare<A : Action, V : ViewState> {

    fun process(action: A)

    fun getProcessedActions(): Flow<A>

}

interface Store<A : Action, V : ViewState> {

    var onEachAction: ((A) -> Unit)?

    fun process(action: A)

    fun observeViewState(): Flow<V>

}

@ExperimentalCoroutinesApi
class SimpleStore<A : Action, V : ViewState> @Inject constructor(
    private val reducer: Reducer<A, V>,
    private val middleWare: MiddleWare<A, V>,
    initialState: V
) : Store<A, V> {

    private var lastState = initialState

    override var onEachAction: ((A) -> Unit)? = null

    override fun process(action: A) {
        middleWare.process(action)
    }

    override fun observeViewState(): Flow<V> {
        return middleWare.getProcessedActions()
            .onEach {
                onEachAction?.invoke(it)
            }
            .map {
                reducer.reduce(lastState, it)
            }
            .onEach {
                lastState = it
            }
            .onStart {
                emit(lastState)
            }
    }

}

interface Reducer<A : Action, V : ViewState> {
    fun reduce(viewState: V, action: A): V
}