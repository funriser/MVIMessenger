package com.funrisestudio.mvimessenger.mvi

import com.funrisestudio.mvimessenger.core.mvi.*
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
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
    fun `should transform middleware actions to states`() = runBlockingTest {
        //Arrange
        val middlewareFlow = flow {
            emit(TestAction.TestAction1)
            emit(TestAction.TestAction2)
        }
        whenever(middleWare.bind(any()))
            .thenReturn(middlewareFlow)
        whenever(reducer.reduce(TestViewState("0"), TestAction.TestAction1))
            .thenReturn(TestViewState("1"))
        whenever(reducer.reduce(TestViewState("1"), TestAction.TestAction2))
            .thenReturn(TestViewState("2"))
        //Act
        val store = SimpleStore(reducer, middleWare, TestViewState("0"))
        val stateFlow = store.observeViewState()
            .take(3)
            .toList()
        //Assert
        val expectedStates = listOf(
            TestViewState("0"), TestViewState("1"), TestViewState("2")
        )
        assertEquals(stateFlow, expectedStates)
    }

}