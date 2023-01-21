package com.dart69.todolist.main.presentation

import androidx.lifecycle.viewModelScope
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.presentation.BaseViewModel
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.home.di.Predefined
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import com.dart69.todolist.splash.domain.usecase.IsAppFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isAppFirstRunUseCase: IsAppFirstRunUseCase,
    private val repository: TaskListRepository,
    private val dispatchers: AvailableDispatchers,
    @Predefined private val smartLists: List<@JvmSuppressWildcards TaskList>
) : BaseViewModel<ScreenState.None>(ScreenState.None) {
    private val isInitialized = AtomicBoolean(false)

    fun initializeRepository() {
        if (isInitialized.compareAndSet(false, true)) {
            viewModelScope.launch(dispatchers.default) {
                if (isAppFirstRunUseCase()) {
                    smartLists.forEach {
                        repository.createNewList(it)
                    }
                }
            }
        }
    }
}