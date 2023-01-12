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

internal sealed class TestScreenEvents : ScreenEvent {
    data class OpenScreen(val route: String) : TestScreenEvents()

    object Close : TestScreenEvents()
}

@ExperimentalCoroutinesApi
internal class MutableEventObserverTest : BaseTest.Default() {
    private lateinit var eventObserver: MutableEventObserver.Default<TestScreenEvents>

    @Before
    override fun beforeEach() {
        eventObserver = MutableEventObserver.Default()
    }

    @Test
    fun sendEvent() = runBlocking {
        val expectedEvents = listOf(
            TestScreenEvents.OpenScreen("home"),
            TestScreenEvents.Close,
            TestScreenEvents.OpenScreen("splash")
        )
        val actualEvents = mutableListOf<TestScreenEvents>()
        val job = launch(UnconfinedTestDispatcher()) {
            eventObserver.observeEvent().take(expectedEvents.size).toList(actualEvents)
        }
        expectedEvents.forEach { eventObserver.sendEvent(it) }
        job.join()
        assertCollectionsEquals(expectedEvents, actualEvents)
    }
}