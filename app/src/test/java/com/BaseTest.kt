package com

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.junit.Before

interface BaseTest {
    fun <T> CoroutineScope.launchWithTimeout(
        timeOut: Long,
        block: suspend CoroutineScope.() -> T
    ): Job

    abstract class Default : BaseTest {
        protected open val timeOut = 300L

        @Before
        abstract fun beforeEach()

        override fun <T> CoroutineScope.launchWithTimeout(
            timeOut: Long,
            block: suspend CoroutineScope.() -> T
        ): Job =
            launch {
                withTimeout(timeOut, block)
            }
    }
}