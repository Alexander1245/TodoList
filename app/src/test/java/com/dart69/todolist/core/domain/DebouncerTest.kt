package com.dart69.todolist.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

internal class DebouncerTest {

    @Test
    fun testDebounce() = runBlocking {
        val counter = AtomicInteger(0)
        val period = 15L
        val callback = suspend {
            counter.incrementAndGet()
            Unit
        }
        val debouncer = buildDebouncer {
            coroutineScope = this@runBlocking
            debounce = period
            action = callback
        }
        repeat(100) {
            debouncer.executeWithDebounce()
        }
        delay(500)
        debouncer.executeWithDebounce()
        delay(100)
        assertEquals(2, counter.get())
    }
}