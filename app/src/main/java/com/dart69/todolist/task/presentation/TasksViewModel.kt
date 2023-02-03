package com.dart69.todolist.task.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.mapResults
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.ScreenEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.home.domain.model.TaskList
import com.dart69.todolist.task.model.SearchingParams
import com.dart69.todolist.task.model.Task
import com.dart69.todolist.task.model.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TasksScreenState(
    val listName: String,
    val isButtonVisible: Boolean,
    val isInputVisible: Boolean,
    val isInputEnabled: Boolean,
    val isProgressVisible: Boolean,
    val tasks: List<Task>,
) : ScreenState {
    val isModificationsAllowed: Boolean
        get() = listName !in TaskList.PREDEFINED.map { it.name }

    companion object {
        fun from(
            savedStateHandle: SavedStateHandle
        ): TasksScreenState {
            val listName = TasksFragmentArgs.fromSavedStateHandle(savedStateHandle).taskListName
            return TasksScreenState(
                listName = listName,
                isButtonVisible = true,
                isInputVisible = false,
                isInputEnabled = false,
                isProgressVisible = false,
                tasks = emptyList(),
            )
        }
    }
}

sealed class TasksEvents : ScreenEvent {
    object ClearTextInput : TasksEvents()

    data class DeleteTaskList(val name: String) : TasksEvents()

    data class NavigateTo(val directions: NavDirections) : TasksEvents()
}

sealed class Filter {
    abstract fun provide(): (Task) -> Boolean

    object ToDo : Filter() {
        override fun provide(): (Task) -> Boolean = {
            !it.isCompleted
        }
    }

    object Completed : Filter() {
        override fun provide(): (Task) -> Boolean = {
            it.isCompleted
        }
    }
}

@HiltViewModel
class TasksViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatchers: AvailableDispatchers,
    private val repository: TasksRepository,
) : CommunicatorViewModel<TasksScreenState, TasksEvents>(TasksScreenState.from(savedStateHandle)),
    TasksViewHolder.Callbacks {
    private val args = TasksFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val filter = MutableStateFlow<Filter>(Filter.ToDo)
    private val isTasksCompleted = MutableStateFlow(false)
    private val listName = MutableStateFlow(args.taskListName)

    init {
        repository.searchBy(SearchingParams.from(listName.value))
        viewModelScope.launch(dispatchers.default) {
            filter.combine(repository.observe()) { filter, results ->
                results.mapResults {
                    it.filter(filter.provide())
                }
            }.collect {
                Log.d("Tasks", it.mapResults {
                    it.map {
                        it.name to it.dueDate
                    }
                }.toString())
                val isReadyToClearInput =
                    screenObserver.currentState.isInputVisible && it is Results.Success
                screenObserver.updateScreenState { screenState ->
                    screenState.copy(
                        isProgressVisible = it is Results.Loading,
                        isInputEnabled = isReadyToClearInput,
                        tasks = if (it is Results.Success) it.data else screenState.tasks,
                    )
                }
                if (isReadyToClearInput) {
                    eventObserver.sendEvent(TasksEvents.ClearTextInput)
                }
            }
        }
    }

    fun toggleTextInput() {
        viewModelScope.launch(dispatchers.default) {
            screenObserver.updateScreenState {
                it.copy(
                    isInputVisible = !it.isInputVisible,
                    isInputEnabled = !it.isInputEnabled,
                    isButtonVisible = !it.isButtonVisible,
                )
            }
        }
    }

    fun createNewTask(name: String) {
        viewModelScope.launch(dispatchers.default) {
            screenObserver.updateScreenState { it.copy(isInputEnabled = false) }
            repository.createNewTask(Task(name, listName.value, isTasksCompleted.value))
        }
    }

    fun toggleNewTaskCompleted(isCompleted: Boolean) {
        isTasksCompleted.value = isCompleted
    }

    override fun onCompletedClick(task: Task) {
        viewModelScope.launch(dispatchers.default) {
            repository.toggleCompleted(task)
        }
    }

    override fun onImportantClick(task: Task) {
        viewModelScope.launch(dispatchers.default) {
            repository.toggleImportant(task)
        }
    }

    override fun onTaskClick(task: Task) {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(
                TasksEvents.NavigateTo(
                    TasksFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(task)
                )
            )
        }
    }

    fun deleteTaskList() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(TasksEvents.DeleteTaskList(listName.value))
        }
    }

    fun deleteTask(position: Int) {
        viewModelScope.launch(dispatchers.default) {
            repository.deleteTask(screenObserver.currentState.tasks[position])
        }
    }

    fun filterByToDo() {
        filter.value = Filter.ToDo
    }

    fun filterByCompleted() {
        filter.value = Filter.Completed
    }
}


