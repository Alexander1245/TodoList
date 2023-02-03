package com.dart69.todolist.taskdetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.di.ApplicationScope
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.ScreenEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.task.domain.AvailableDates
import com.dart69.todolist.task.domain.UndefinedDateException
import com.dart69.todolist.task.model.Task
import com.dart69.todolist.task.model.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

data class TaskDetailsScreenState(
    val task: Task,
) : ScreenState {
    fun toggleImportant(): TaskDetailsScreenState =
        copy(task = task.copy(isImportant = !task.isImportant))

    fun toggleCompleted(): TaskDetailsScreenState =
        copy(task = task.copy(isCompleted = !task.isCompleted))

    fun changeName(newName: String): TaskDetailsScreenState =
        copy(task = task.copy(name = newName))

    fun changeNote(newNote: String): TaskDetailsScreenState =
        copy(task = task.copy(note = newNote))

    fun changeDueDate(date: AvailableDates?): TaskDetailsScreenState =
        copy(task = task.copy(dueDate = date))

    interface Creator {
        fun create(): TaskDetailsScreenState

        class Default @Inject constructor(
            private val savedStateHandle: SavedStateHandle
        ) : Creator {
            override fun create(): TaskDetailsScreenState =
                TaskDetailsScreenState(TaskDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).task)
        }
    }
}

sealed class TaskDetailsEvents : ScreenEvent {
    object Close : TaskDetailsEvents()

    data class InitializeInputs(val name: String, val note: String) : TaskDetailsEvents()

    object ShowPopup : TaskDetailsEvents()

    object PickDate : TaskDetailsEvents()
}

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    creator: TaskDetailsScreenState.Creator,
    private val dispatchers: AvailableDispatchers,
    private val repository: TasksRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
    private val dateFactory: AvailableDates.Factory,
) : CommunicatorViewModel<TaskDetailsScreenState, TaskDetailsEvents>(creator.create()) {
    private val task: Task get() = screenObserver.currentState.task
    private val isInputsInitialized = AtomicBoolean(false)

    fun initializeInputs() {
        if (isInputsInitialized.compareAndSet(false, true)) {
            viewModelScope.launch(dispatchers.default) {
                eventObserver.sendEvent(
                    TaskDetailsEvents.InitializeInputs(
                        name = task.name,
                        note = task.note,
                    )
                )
            }
        }
    }

    fun toggleCompleted() {
        screenObserver.updateScreenState {
            it.toggleCompleted()
        }
    }

    fun toggleImportant() {
        screenObserver.updateScreenState {
            it.toggleImportant()
        }
    }

    fun onNameChanged(newName: String) {
        screenObserver.updateScreenState {
            it.changeName(newName)
        }
    }

    fun onNoteChanged(note: String) {
        screenObserver.updateScreenState {
            it.changeNote(note)
        }
    }

    fun deleteTask() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(TaskDetailsEvents.Close)
        }
        applicationScope.launch(dispatchers.default) {
            repository.deleteTask(task)
        }
    }

    fun editAndClose() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(TaskDetailsEvents.Close)
        }
        applicationScope.launch(dispatchers.default) {
            repository.editTask(task)
        }
    }

    fun onPopupMenuItemClick(id: Int): Boolean {
        try {
            val date = when (id) {
                R.id.itemToday -> AvailableDates.Today
                R.id.itemTomorrow -> AvailableDates.Tomorrow
                R.id.itemNextWeek -> AvailableDates.NextWeek
                R.id.itemPickDate -> throw UndefinedDateException()
                else -> error("Illegal popup id: $id")
            }
            screenObserver.updateScreenState { it.changeDueDate(date) }
        } catch (_: UndefinedDateException) {
            pickDate()
        }
        return true
    }

    fun pickDate() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(TaskDetailsEvents.PickDate)
        }
    }

    fun openDueDateMenu() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(TaskDetailsEvents.ShowPopup)
        }
    }

    fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        val date = dateFactory.create(LocalDate.of(year, month + 1, dayOfMonth).toEpochDay())
        screenObserver.updateScreenState {
            it.changeDueDate(date)
        }
    }
}