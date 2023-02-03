package com.dart69.todolist.home.presentation

import android.util.Log
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
import com.dart69.todolist.home.data.TaskListRepositoryImpl
import com.dart69.todolist.home.domain.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeScreenState : ScreenState {
    object Loading : HomeScreenState()

    data class Error(@StringRes val message: Int) : HomeScreenState()

    data class Success(val tasks: List<TaskList>) : HomeScreenState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TaskListRepositoryImpl,
    private val dispatchers: AvailableDispatchers,
) : CommunicatorViewModel<HomeScreenState, NavigationEvent>(HomeScreenState.Loading), Searchable,
    SearchView.OnQueryTextListener {
    init {
        viewModelScope.launch(dispatchers.default) {
            repository.observe().collect {
                Log.d("Home", "results $it")
                if (it is Results.Error) {
                    Log.d("Home", "error ${it.throwable}")
                }
                screenObserver.sendScreenState(it.toScreenState())
            }
        }
    }

    fun createNewList(name: String) {
        viewModelScope.launch(dispatchers.default) {
            repository.createNewList(TaskList(name))
        }
    }

    fun deleteTaskList(name: String) {
        viewModelScope.launch(dispatchers.default) {
            screenObserver.sendScreenState(HomeScreenState.Loading)
            repository.deleteTaskListByName(name)
        }
    }

    override fun search(query: String) {
        val name = query.trim()
        repository.updateSearchQuery(name)
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

    fun onTaskListClick(taskList: TaskList) {
        viewModelScope.launch(dispatchers.default) {
            Log.d("Tasks", "onTaskListClick: $taskList")
            val name = taskList.name
            val direction = HomeFragmentDirections.actionHomeFragmentToTaskListFragment(name)
            eventObserver.sendEvent(NavigationEvent(direction))
        }
    }
}

private fun Results<List<TaskList>>.toScreenState(): HomeScreenState =
    when (this) {
        is Results.Loading -> HomeScreenState.Loading
        is Results.Success -> HomeScreenState.Success(data)
        is Results.Error -> HomeScreenState.Error(R.string.internal_exception)
    }