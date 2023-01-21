package com.dart69.todolist.core.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface ScreenState {
    object None : ScreenState
}

interface ScreenEvent

interface Screen : LifecycleOwner {
    fun requireLifecycleScope(): LifecycleCoroutineScope

    fun <T> Flow<T>.collectWithLifecycle(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: FlowCollector<T>
    ) {
        val flow = this
        requireLifecycleScope().launch {
            repeatOnLifecycle(lifecycleState) {
                flow.collect(collector)
            }
        }
    }
}