package com.dart69.todolist.home.data

import com.dart69.todolist.core.coroutines.*
import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.di.Predefined
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    private val dispatchers: AvailableDispatchers,
    private val mapper: BidirectionalMapper<TaskListEntity, TaskList>,
    private val dao: TaskListDao,
    @Predefined private val predefinedTaskList: List<@JvmSuppressWildcards TaskList>,
    private val queryMapper: QueryMapper,
) : TaskListRepository {
    private val taskLists = MutableResultsStateFlow(Results.Success(emptyList<TaskList>()))
    private val lastQuery = MutableStateFlow(queryMapper.map(""))

    override fun observeTaskLists(): ResultsStateFlow<List<TaskList>> = taskLists.asStateFlow()

    private suspend fun emitLastQueryWith(action: (suspend () -> Unit)? = null) {
        taskLists.emitResults {
            withContext(dispatchers.io) {
                action?.invoke()
                dao.searchBy(lastQuery.value).map(mapper::toModel)
            }
        }
    }

    override suspend fun emitSearchQuery(query: String) {
        emitLastQueryWith {
            lastQuery.emit(queryMapper.map(query))
        }
    }

    override suspend fun emitLastQuery() {
        emitLastQueryWith()
    }

    override suspend fun createNewList(list: TaskList) {
        emitLastQueryWith {
            dao.insert(mapper.toEntity(list))
        }
    }

    override suspend fun createSmartLists() {
        emitLastQueryWith {
            dao.upsert(*predefinedTaskList.map(mapper::toEntity).toTypedArray())
        }
    }
}