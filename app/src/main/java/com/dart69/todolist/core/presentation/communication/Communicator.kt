package com.dart69.todolist.core.presentation.communication

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface Receiver<T> {
    suspend fun receive(): SharedFlow<T>
}

interface Sender<T> {
    suspend fun send(value: T)
}

interface Communicator<T>: Receiver<T>, Sender<T> {

    abstract class Default<T>: Communicator<T> {
        private val flow = MutableSharedFlow<T>()

        override suspend fun receive(): SharedFlow<T> = flow.asSharedFlow()

        override suspend fun send(value: T) {
            flow.emit(value)
        }
    }
}