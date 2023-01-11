package com.dart69.todolist.core.presentation

import com.BaseTest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Queue
import java.util.ArrayDeque

internal sealed class TestScreenState : ScreenState {
    object Loading : TestScreenState()

    data class Completed(val data: String) : TestScreenState()
}

internal class MutableScreenStateObserverTest : BaseTest.Default() {
    private lateinit var observer: MutableScreenStateObserver.Default<TestScreenState>

    @Before
    override fun beforeEach() {
        observer = MutableScreenStateObserver.Default(TestScreenState.Loading)
    }

    @Test
    fun sendScreenState() = runBlocking {
        val expectedScreenStates: Queue<TestScreenState> = ArrayDeque(
            listOf(
                TestScreenState.Loading,
                TestScreenState.Completed("first completed"),
                TestScreenState.Completed("second completed"),
                TestScreenState.Loading,
            )
        )
        launchWithTimeout(timeOut) {
            observer.observeScreenState().take(expectedScreenStates.size).collect { actual ->
                val expected = expectedScreenStates.poll()
                assertEquals(expected, actual)
            }
        }
        expectedScreenStates.forEach { observer.sendScreenState(it) }
    }
}