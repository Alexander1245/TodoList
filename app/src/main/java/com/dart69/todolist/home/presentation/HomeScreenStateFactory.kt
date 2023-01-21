package com.dart69.todolist.home.presentation

import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.home.domain.model.TaskList
import javax.inject.Inject

interface HomeScreenStateFactory {
    fun create(results: Results<List<TaskList>>): HomeScreenState

    class Default @Inject constructor() : HomeScreenStateFactory {
        override fun create(results: Results<List<TaskList>>): HomeScreenState =
            when (results) {
                is Results.Loading -> HomeScreenState.Loading
                is Results.Error -> HomeScreenState.Error(R.string.internal_exception)
                is Results.Success -> HomeScreenState.Success(results.data)
            }
    }
}