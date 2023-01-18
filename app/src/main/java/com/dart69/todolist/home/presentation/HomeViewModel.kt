package com.dart69.todolist.home.presentation

import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.viewModelScope
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.NavigationEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.core.presentation.Searchable
import com.dart69.todolist.core.presentation.communication.Receiver
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

sealed class HomeScreenState : ScreenState {
    object Loading : HomeScreenState()

    data class Error(@StringRes val message: Int) : HomeScreenState()

    data class Success(val tasks: List<TaskList>) : HomeScreenState()
}

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TaskListRepository,
    private val dispatchers: AvailableDispatchers,
    private val communicator: Receiver<TaskList>,
) : CommunicatorViewModel<HomeScreenState, NavigationEvent>(HomeScreenState.Loading), Searchable,
    SearchView.OnQueryTextListener {
    private val lastQuery = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        viewModelScope.launch(dispatchers.default) {
            communicator.receive().flatMapConcat {
                repository.createList(it)
            }.collect {
                screenObserver.sendScreenState(it.toScreenState())
            }
        }

        viewModelScope.launch(dispatchers.default) {
            repository.createSmartLists().collect()
            lastQuery.flatMapConcat {
                repository.search(it)
            }.collect {
                screenObserver.sendScreenState(it.toScreenState())
            }
        }
    }

    @MainThread
    override fun search(query: String) {
        val name = query.trim()
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatchers.default) {
            delay(DEBOUNCE_TIME)
            lastQuery.emit(name)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let(this::search)
        return true
    }

    fun openCreationDialog() {
        viewModelScope.launch(dispatchers.default) {
            val direction = HomeFragmentDirections.actionHomeFragmentToCreateListDialogFragment()
            eventObserver.sendEvent(NavigationEvent(direction))
        }
    }

    companion object {
        const val DEBOUNCE_TIME = 500L
    }
}

private fun Results<List<TaskList>>.toScreenState(): HomeScreenState =
    when (this) {
        is Results.Loading -> HomeScreenState.Loading
        is Results.Success -> HomeScreenState.Success(data)
        is Results.Error -> HomeScreenState.Error(R.string.internal_exception)
    }