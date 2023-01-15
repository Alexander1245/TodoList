package com.dart69.todolist.createlist.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.takeSuccess
import com.dart69.todolist.core.data.exceptions.ErrorMapper
import com.dart69.todolist.core.data.exceptions.RoomException
import com.dart69.todolist.core.presentation.CloseScreenEvent
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.Screen
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreateListScreenState : ScreenState {

    object EmptyName : CreateListScreenState()

    object Success : CreateListScreenState()

    object Loading : CreateListScreenState()

    data class InvalidName(@StringRes val message: Int) : CreateListScreenState()

    companion object {
        fun fromResults(
            results: Results<List<TaskList>>,
            errorMapper: ErrorMapper<Throwable, RoomException>,
        ): CreateListScreenState = when (results) {
            is Results.Success -> Success
            is Results.Loading -> Loading
            is Results.Error -> InvalidName(errorMapper.map(results.throwable).messageResource)
        }
    }
}

@HiltViewModel
class CreateListViewModel @Inject constructor(
    private val repository: TaskListRepository,
    private val dispatchers: AvailableDispatchers,
    private val errorMapper: ErrorMapper<Throwable, RoomException>,
) : CommunicatorViewModel<CreateListScreenState, CloseScreenEvent>(CreateListScreenState.EmptyName) {

    init {
        viewModelScope.launch(dispatchers.default) {
            repository.observeTaskLists().collect {
                screenObserver.sendScreenState(CreateListScreenState.fromResults(it, errorMapper))
            }
        }

        viewModelScope.launch(dispatchers.default) {
            screenObserver.observeScreenState().collect {
                if(it is CreateListScreenState.Success) {
                    eventObserver.sendEvent(CloseScreenEvent)
                }
            }
        }
    }

    fun close() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(CloseScreenEvent)
        }
    }

    fun createNewList(name: String) {
        viewModelScope.launch(dispatchers.default) {
            repository.createNewList(TaskList(name))
        }
    }
}