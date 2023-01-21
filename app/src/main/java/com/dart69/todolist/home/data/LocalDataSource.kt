package com.dart69.todolist.home.data

import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TasksLocalDataSource {
    suspend fun insert(taskList: TaskList)

    suspend fun upsert(taskLists: List<TaskList>)

    suspend fun searchBy(query: String): List<TaskList>

    suspend fun findByName(name: String): TaskList?

    class Default @Inject constructor(
        private val mapper: BidirectionalMapper<TaskListEntity, TaskList>,
        private val dispatchers: AvailableDispatchers,
        private val dao: TaskListDao,
    ): TasksLocalDataSource {
        private val queryMapper: (String) -> String = { "%$it%" }

        override suspend fun insert(taskList: TaskList) = withContext(dispatchers.io) {
            dao.insert(mapper.toEntity(taskList))
        }

        override suspend fun upsert(taskLists: List<TaskList>) = withContext(dispatchers.io) {
            dao.upsert(taskLists.map(mapper::toEntity))
        }

        override suspend fun searchBy(query: String): List<TaskList> = withContext(dispatchers.io) {
            dao.searchBy(queryMapper(query)).map(mapper::toModel)
        }

        override suspend fun findByName(name: String): TaskList? = withContext(dispatchers.io) {
            dao.findByName(name)?.let(mapper::toModel)
        }
    }
}