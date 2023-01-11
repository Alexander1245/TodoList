package com.dart69.todolist.core.presentation

import com.BaseTest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.ArrayDeque
import java.util.Queue

internal sealed class TestScreenEvents: ScreenEvent {
    data class OpenScreen(val route: String) : TestScreenEvents()

    object Close : TestScreenEvents()
}

internal class MutableEventObserverTest: BaseTest.Default() {
    private lateinit var eventObserver: MutableEventObserver.Default<TestScreenEvents>

    @Before
    override fun beforeEach() {
        eventObserver = MutableEventObserver.Default()
    }

    @Test
    fun sendEvent() = runBlocking {
        val expectedEvents: Queue<TestScreenEvents> = ArrayDeque(
            listOf(
                TestScreenEvents.OpenScreen("home"),
                TestScreenEvents.Close,
                TestScreenEvents.OpenScreen("splash")
            )
        )
        launchWithTimeout(timeOut) {
            eventObserver.observeEvent().take(expectedEvents.size).collect { actual ->
                val expected = expectedEvents.poll()
                assertEquals(expected, actual)
            }
        }
        expectedEvents.forEach { eventObserver.sendEvent(it) }
    }
}