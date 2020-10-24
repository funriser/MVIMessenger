package com.funrisestudio.mvimessenger.core.mvi

import com.funrisestudio.mvimessenger.core.withLatestFrom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val BUF_CAPACITY = 64

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

    private val actionsFlow = MutableSharedFlow<A>(replay = 1, extraBufferCapacity = BUF_CAPACITY)

    override fun process(action: A) {
        actionsFlow.tryEmit(action)
    }

    override fun init(scope: CoroutineScope, initialState: V) {
        _viewStateFlow = MutableStateFlow(initialState)
        middleWare.bind(actionsFlow)
            .onEach {
                actionsFlow.tryEmit(it)
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