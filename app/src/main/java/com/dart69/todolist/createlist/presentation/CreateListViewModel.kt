package com.dart69.todolist.createlist.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.presentation.CloseScreenEvent
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.core.presentation.communication.Sender
import com.dart69.todolist.home.domain.NameParser
import com.dart69.todolist.home.domain.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CreateListScreenState(
    val isCreateButtonEnabled: Boolean,
) : ScreenState {

    abstract class InvalidName : CreateListScreenState(false) {
        open val argument: Any = ""
        abstract val message: Int
    }

    data class NotUniqueName(override val argument: String) : InvalidName() {
        override val message: Int = R.string.item_already_exists
    }

    object EmptyName : InvalidName() {
        override val message: Int = R.string.name_cant_be_empty
    }

    data class VeryLongName(override val argument: Int) : InvalidName() {
        override val message: Int = R.string.very_long_name
    }

    data class CorrectName(val name: String) : CreateListScreenState(true)

    object Loading : CreateListScreenState(false)
}

@HiltViewModel
class CreateListViewModel @Inject constructor(
    private val communicator: Sender<TaskList>,
    private val dispatchers: AvailableDispatchers,
    private val nameParser: NameParser,
    private val screenStateFactory: CreateListScreenStateFactory,
) : CommunicatorViewModel<CreateListScreenState, CloseScreenEvent>(CreateListScreenState.EmptyName) {
    private var searchJob: Job? = null

    fun close() {
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(CloseScreenEvent)
        }
    }

    fun createNewList() {
        close()
        viewModelScope.launch(dispatchers.default) {
            val state = observeScreenState().value
            if (state is CreateListScreenState.CorrectName) {
                communicator.send(TaskList(state.name))
            }
        }
    }

    fun onTextChanged(text: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatchers.default) {
            val name = text.trim()
            screenObserver.sendScreenState(CreateListScreenState.Loading)
            delay(DEBOUNCE_TIME)
            nameParser.matches(name).collect {
                screenObserver.sendScreenState(screenStateFactory.create(name, it))
            }
        }
    }

    private companion object {
        const val DEBOUNCE_TIME = 333L
    }
}