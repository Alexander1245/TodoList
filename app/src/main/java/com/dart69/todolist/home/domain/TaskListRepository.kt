package com.dart69.todolist.home.domain

import com.dart69.todolist.core.coroutines.ResultsStateFlow
import com.dart69.todolist.home.domain.model.TaskList

interface TaskListRepository {
    fun observeTaskLists(): ResultsStateFlow<List<TaskList>>

    suspend fun emitSearchQuery(query: String)

    suspend fun emitLastQuery()

    suspend fun createNewList(list: TaskList)

    suspend fun createSmartLists()
}