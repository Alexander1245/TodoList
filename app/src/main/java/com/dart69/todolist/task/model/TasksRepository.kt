package com.dart69.todolist.task.model

import com.dart69.todolist.core.coroutines.ResultsFlow
import com.dart69.todolist.home.domain.model.TaskList

sealed class SearchingParams {
    data class ByName(val name: String) : SearchingParams()

    data class ByImportant(val isImportant: Boolean = true) : SearchingParams()

    object LoadAll : SearchingParams()

    companion object {
        fun from(listName: String): SearchingParams =
            when (listName) {
                TaskList.Tasks.name -> LoadAll
                TaskList.Important.name -> ByImportant()
                else -> ByName(listName)
            }
    }
}

interface TasksRepository {
    fun searchBy(params: SearchingParams)

    fun observe(): ResultsFlow<List<Task>>

    fun findTaskById(id: Long): ResultsFlow<Task>
    suspend fun editTask(task: Task)

    suspend fun createNewTask(task: Task)

    suspend fun toggleCompleted(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun toggleImportant(task: Task)
}