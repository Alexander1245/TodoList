package com.dart69.todolist.home.domain

import com.dart69.todolist.core.coroutines.ResultsFlow
import com.dart69.todolist.home.domain.model.TaskList

interface TaskListRepository {
    fun observe(): ResultsFlow<List<TaskList>>

    fun findTaskByName(name: String): ResultsFlow<TaskList?>

    fun updateSearchQuery(query: String)

    suspend fun createNewList(taskList: TaskList)

    companion object {
        const val MAX_NAME_LENGTH = 20
    }
}