package com.dart69.todolist.core.presentation

import com.BaseTest
import com.assertCollectionsEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

internal sealed class TestScreenState : ScreenState {
    object Loading : TestScreenState()

    data class Completed(val data: String) : TestScreenState()
}

@ExperimentalCoroutinesApi
internal class MutableScreenStateObserverTest : BaseTest.Default() {
    private lateinit var observer: MutableScreenStateObserver.Default<TestScreenState>

    @Before
    override fun beforeEach() {
        observer = MutableScreenStateObserver.Default(TestScreenState.Loading)
    }

    @Test
    fun sendScreenState() = runBlocking {
        val expectedScreenStates = listOf(
            TestScreenState.Loading,
            TestScreenState.Completed("first completed"),
            TestScreenState.Completed("second completed"),
            TestScreenState.Loading,
        )
        val actualScreenStates = mutableListOf<TestScreenState>()
        val job = launch(UnconfinedTestDispatcher()) {
            observer.observeScreenState().take(expectedScreenStates.size).toList(actualScreenStates)
        }
        expectedScreenStates.forEach { observer.sendScreenState(it) }
        job.join()
        assertCollectionsEquals(expectedScreenStates, actualScreenStates)
    }
}