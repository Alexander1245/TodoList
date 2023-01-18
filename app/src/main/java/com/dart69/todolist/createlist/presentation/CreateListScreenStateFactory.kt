package com.dart69.todolist.createlist.presentation

import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.home.data.error.*
import com.dart69.todolist.home.domain.TaskListRepository.Companion.MAX_NAME_LENGTH
import javax.inject.Inject

interface CreateListScreenStateFactory {
    fun create(name: String, results: Results<Boolean>): CreateListScreenState

    class Default @Inject constructor() : CreateListScreenStateFactory {

        private fun mapErrors(
            name: String,
            results: Results.Error<Boolean>,
        ) = when (results.throwable) {
            is EmptyNameException -> CreateListScreenState.EmptyName
            is NameTooLongException -> CreateListScreenState.VeryLongName(MAX_NAME_LENGTH)
            is NotUniqueNameException -> CreateListScreenState.NotUniqueName(name)
            else -> throw results.throwable
        }

        override fun create(name: String, results: Results<Boolean>): CreateListScreenState =
            when (results) {
                is Results.Success -> CreateListScreenState.CorrectName(name)
                is Results.Loading -> CreateListScreenState.Loading
                is Results.Error -> mapErrors(name, results)
            }
    }
}

