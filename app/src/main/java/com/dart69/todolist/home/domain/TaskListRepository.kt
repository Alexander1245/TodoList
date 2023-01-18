package com.dart69.todolist.home.domain

import com.dart69.todolist.core.coroutines.ResultsFlow
import com.dart69.todolist.home.domain.model.TaskList

interface TaskListRepository {
    suspend fun search(query: String): ResultsFlow<List<TaskList>>

    suspend fun createSmartLists(): ResultsFlow<List<TaskList>>

    suspend fun createList(taskList: TaskList): ResultsFlow<List<TaskList>>

    suspend fun findTaskByName(name: String): ResultsFlow<TaskList?>

    companion object {
        const val MAX_NAME_LENGTH = 20
    }
}