package com.dart69.todolist.core.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

interface Debouncer {
    fun executeWithDebounce(action: suspend () -> Unit)

    class Default(
        private val timeOut: Long,
        private val coroutineScope: CoroutineScope,
    ) : Debouncer {
        private var job: Job? = null

        override fun executeWithDebounce(action: suspend () -> Unit) {
            job?.cancel()
            job = coroutineScope.launch {
                delay(timeOut)
                action()
            }
        }
    }

    interface Builder {
        var coroutineScope: CoroutineScope
        var debounce: Long

        fun build(): Debouncer

        class Default : Builder {
            override var coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
            override var debounce: Long = 0L
                set(value) {
                    require(debounce >= 0)
                    field = value
                }


            override fun build(): Debouncer =
                Default(debounce, coroutineScope)
        }
    }
}

fun buildDebouncer(block: Debouncer.Builder.() -> Unit): Debouncer =
    Debouncer.Builder.Default().run {
        block()
        build()
    }
