package com.dart69.todolist.core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<SS : ScreenState>(
    initialState: SS,
    protected val screenObserver: MutableScreenStateObserver<SS> = MutableScreenStateObserver.Default(initialState)
) : ViewModel(), ScreenStateObserver<SS> {

    override fun observeScreenState(): StateFlow<SS> = screenObserver.observeScreenState()
}

abstract class CommunicatorViewModel<SS : ScreenState, SE : ScreenEvent>(
    initialState: SS,
    protected val eventObserver: MutableEventObserver<SE> = MutableEventObserver.Default()
) : BaseViewModel<SS>(initialState), Communicator<SS, SE> {

    override fun observeEvent(): SharedFlow<SE> = eventObserver.observeEvent()
}