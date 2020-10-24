package com.funrisestudio.mvimessenger.mvi

import com.funrisestudio.mvimessenger.core.mvi.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class SimpleStoreTest {

    sealed class TestAction: Action {
        object TestAction1: TestAction()
        object TestAction2: TestAction()
    }

    data class TestViewState(val id: String): ViewState

    private val middleWare: MiddleWare<TestAction, TestViewState> = mock()
    private val reducer: Reducer<TestAction, TestViewState> = mock()

    @Test
    fun `should transform middleware actions to states`() = runBlocking {
        //Arrange
        val middlewareFlow = flow {
            delay(50)
            emit(TestAction.TestAction1)
            delay(50)
            emit(TestAction.TestAction2)
        }
        whenever(middleWare.bind(any()))
            .thenReturn(middlewareFlow)
        whenever(reducer.reduce(TestViewState("0"), TestAction.TestAction1))
            .thenReturn(TestViewState("1"))
        whenever(reducer.reduce(TestViewState("1"), TestAction.TestAction2))
            .thenReturn(TestViewState("2"))
        //Act
        val store = SimpleStore(reducer, middleWare)
        //Global scope to not block current context with infinite waiting of subscriptions
        //started inside launch methode
        store.init(GlobalScope, TestViewState("0"))
        val list = store.viewStateFlow
            .take(3)
            .toList()
        //Assert
        val expectedStates = listOf(
            TestViewState("0"), TestViewState("1"), TestViewState("2")
        )
        assertEquals(expectedStates, list)
    }

}