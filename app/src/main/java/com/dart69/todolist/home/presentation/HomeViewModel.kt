package com.dart69.todolist.home.presentation

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.viewModelScope
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.data.exceptions.ErrorMapper
import com.dart69.todolist.core.data.exceptions.RoomException
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.NavigationEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.core.presentation.Searchable
import com.dart69.todolist.core.util.Logger
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeScreenState : ScreenState {
    object Loading : HomeScreenState()

    data class Error(val exception: RoomException) : HomeScreenState()

    data class Completed(val tasks: List<TaskList>) : HomeScreenState()

    companion object {

        fun fromResults(
            results: Results<List<TaskList>>,
            errorMapper: ErrorMapper<Throwable, RoomException>
        ): HomeScreenState =
            when (results) {
                is Results.Loading -> Loading
                is Results.Success -> Completed(results.data)
                is Results.Error -> Error(errorMapper.map(results.throwable))
            }
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TaskListRepository,
    private val dispatchers: AvailableDispatchers,
    private val errorMapper: ErrorMapper<Throwable, RoomException>,
    private val logger: Logger,
) : CommunicatorViewModel<HomeScreenState, NavigationEvent>(HomeScreenState.Loading), Searchable,
    SearchView.OnQueryTextListener {

    init {
        viewModelScope.launch(dispatchers.default) {
            repository.createSmartLists()
        }

        viewModelScope.launch(dispatchers.default) {
            repository.observeTaskLists().collect {
                screenObserver.sendScreenState(HomeScreenState.fromResults(it, errorMapper))
            }
        }

        viewModelScope.launch(dispatchers.default) {
            screenObserver.observeScreenState().collect {
                if (it is HomeScreenState.Error) {
                    logger.log(it.exception)
                }
            }
        }
    }

    override fun search(query: String) {
        viewModelScope.launch(dispatchers.default) {
            repository.emitSearchQuery(query)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let(this::search)
        return true
    }

    fun addNewList() {
        viewModelScope.launch(dispatchers.default) {
            val direction = HomeFragmentDirections.actionHomeFragmentToCreateListDialogFragment()
            eventObserver.sendEvent(NavigationEvent(direction))
        }
    }

    fun tryAgain() {
        viewModelScope.launch(dispatchers.default) {
            repository.emitLastQuery()
        }
    }
}