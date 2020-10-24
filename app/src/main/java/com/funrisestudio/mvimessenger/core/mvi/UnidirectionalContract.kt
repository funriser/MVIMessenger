package com.funrisestudio.mvimessenger.core.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*


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

interface Reducer<A : Action, S : ViewState> {
    fun reduce(viewState: S, action: A): S
}