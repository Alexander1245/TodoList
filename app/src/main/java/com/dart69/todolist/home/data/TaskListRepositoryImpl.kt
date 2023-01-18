package com.dart69.todolist.home.data

import com.dart69.todolist.core.coroutines.ResultsFlow
import com.dart69.todolist.core.coroutines.resultsFlowOf
import com.dart69.todolist.home.di.Predefined
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    @Predefined private val smartLists: List<@JvmSuppressWildcards TaskList>,
    private val localDataSource: TasksLocalDataSource,
) : TaskListRepository {
    private val lastQuery = MutableStateFlow(INITIAL_QUERY)

    private fun taskResultsFlowOf(block: suspend () -> Unit): ResultsFlow<List<TaskList>> =
        resultsFlowOf {
            block()
            localDataSource.searchBy(lastQuery.value)
        }

    override suspend fun search(query: String): ResultsFlow<List<TaskList>> =
        taskResultsFlowOf {
            lastQuery.emit(query)
        }

    override suspend fun createSmartLists(): ResultsFlow<List<TaskList>> =
        taskResultsFlowOf {
            localDataSource.upsert(smartLists)
        }

    override suspend fun createList(taskList: TaskList): ResultsFlow<List<TaskList>> =
        taskResultsFlowOf {
            localDataSource.insert(taskList)
        }

    override suspend fun findTaskByName(name: String): ResultsFlow<TaskList?> =
        resultsFlowOf {
            localDataSource.findByName(name)
        }

    private companion object {
        const val INITIAL_QUERY = ""
    }
}

