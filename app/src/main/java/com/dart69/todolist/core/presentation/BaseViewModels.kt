package com.dart69.todolist.core.presentation

import androidx.lifecycle.ViewModel
import com.dart69.todolist.core.coroutines.AppDispatchers
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

private typealias DefaultScreenObserver<T> = MutableScreenStateObserver.Default<T>
private typealias DefaultEventObserver<T> = MutableEventObserver.Default<T>

abstract class BaseViewModel<SS : ScreenState>(
    initial: SS,
    protected val screenObserver: MutableScreenStateObserver<SS> = DefaultScreenObserver(initial),
) : ViewModel(), ScreenStateObserver<SS> {

    override fun observeScreenState(): StateFlow<SS> = screenObserver.observeScreenState()
}

abstract class CommunicatorViewModel<SS : ScreenState, SE : ScreenEvent>(
    initial: SS,
    screenObserver: MutableScreenStateObserver<SS> = DefaultScreenObserver(initial),
    protected val eventObserver: MutableEventObserver<SE> = DefaultEventObserver(),
) : BaseViewModel<SS>(initial, screenObserver), Communicator<SS, SE> {

    override fun observeEvent(): SharedFlow<SE> = eventObserver.observeEvent()
}