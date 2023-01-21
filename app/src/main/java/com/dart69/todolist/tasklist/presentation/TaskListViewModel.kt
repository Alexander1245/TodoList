package com.dart69.todolist.tasklist.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.presentation.BaseViewModel
import com.dart69.todolist.core.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskListScreenState(val name: String): ScreenState {
    companion object {
        val INITIAL = TaskListScreenState("")
    }
}

@HiltViewModel
class TaskListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatchers: AvailableDispatchers,
): BaseViewModel<TaskListScreenState>(TaskListScreenState.INITIAL) {
    private val args = TaskListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        viewModelScope.launch(dispatchers.default) {
            screenObserver.sendScreenState(TaskListScreenState(args.taskListName))
        }
    }
}