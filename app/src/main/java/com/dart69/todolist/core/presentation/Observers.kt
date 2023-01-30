package com.dart69.todolist.core.presentation

import kotlinx.coroutines.flow.*

interface EventObserver<T : ScreenEvent> {
    fun observeEvent(): SharedFlow<T>
}

interface MutableEventObserver<T : ScreenEvent> : EventObserver<T> {
    suspend fun sendEvent(event: T)

    class Default<T : ScreenEvent> : MutableEventObserver<T> {
        private val events = MutableSharedFlow<T>()

        override fun observeEvent(): SharedFlow<T> = events.asSharedFlow()

        override suspend fun sendEvent(event: T) = events.emit(event)
    }
}

interface ScreenStateObserver<T : ScreenState> {
    fun observeScreenState(): StateFlow<T>
}

interface MutableScreenStateObserver<T : ScreenState> : ScreenStateObserver<T> {
    suspend fun sendScreenState(screenState: T)

    fun updateScreenState(transform: (T) -> T)

    val currentState: T

    class Default<T : ScreenState>(
        initial: T
    ) : MutableScreenStateObserver<T> {
        private val screenStates = MutableStateFlow(initial)

        override val currentState: T get() = screenStates.value

        override fun observeScreenState(): StateFlow<T> = screenStates.asStateFlow()

        override suspend fun sendScreenState(screenState: T) = screenStates.emit(screenState)

        override fun updateScreenState(transform: (T) -> T) = screenStates.update(transform)
    }
}

interface Communicator<SS : ScreenState, SE : ScreenEvent> : ScreenStateObserver<SS>,
    EventObserver<SE>