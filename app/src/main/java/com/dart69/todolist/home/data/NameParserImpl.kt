package com.dart69.todolist.home.data

import com.dart69.todolist.core.coroutines.ResultsFlow
import com.dart69.todolist.core.coroutines.resultsFlowOf
import com.dart69.todolist.home.data.error.EmptyNameException
import com.dart69.todolist.home.data.error.NameTooLongException
import com.dart69.todolist.home.data.error.NotUniqueNameException
import com.dart69.todolist.home.domain.NameParser
import com.dart69.todolist.home.domain.TaskListRepository
import javax.inject.Inject

class NameParserImpl @Inject constructor(
    private val localDataSource: TasksLocalDataSource,
) : NameParser {

    override fun matches(name: String): ResultsFlow<Boolean> = resultsFlowOf {
        when {
            name.isBlank() -> throw EmptyNameException()
            name.length > TaskListRepository.MAX_NAME_LENGTH -> throw NameTooLongException()
            localDataSource.findByName(name) != null -> throw NotUniqueNameException()
            else -> true
        }
    }
}

