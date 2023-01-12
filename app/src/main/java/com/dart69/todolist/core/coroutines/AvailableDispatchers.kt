package com.dart69.todolist.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface AvailableDispatchers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}

class AppDispatchers @Inject constructor() : AvailableDispatchers {
    override val io: CoroutineDispatcher get() = Dispatchers.IO
    override val default: CoroutineDispatcher get() = Dispatchers.Default
    override val main: CoroutineDispatcher get() = Dispatchers.Main
}