package com.dart69.todolist.task.data

import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.task.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TasksLocalDataSource {
    suspend fun loadAll(): List<Task>

    suspend fun findByListName(listName: String): List<Task>

    suspend fun findByImportant(isImportant: Boolean): List<Task>

    suspend fun insert(task: Task)

    suspend fun update(task: Task)

    suspend fun delete(task: Task)

    class Default @Inject constructor(
        private val dao: TasksDao,
        private val dispatchers: AvailableDispatchers,
        private val mapper: BidirectionalMapper<TaskEntity, Task>
    ) : TasksLocalDataSource {

        override suspend fun loadAll(): List<Task> =
            withContext(dispatchers.io) {
                dao.loadAll().map(mapper::toModel)
            }

        override suspend fun findByListName(listName: String): List<Task> =
            withContext(dispatchers.io) {
                dao.loadByListName(listName).map(mapper::toModel)
            }

        override suspend fun findByImportant(isImportant: Boolean): List<Task> =
            withContext(dispatchers.io) {
                dao.loadByImportant(isImportant).map(mapper::toModel)
            }

        override suspend fun insert(task: Task) =
            withContext(dispatchers.io) {
                dao.insert(mapper.toEntity(task))
            }

        override suspend fun update(task: Task) =
            withContext(dispatchers.io) {
                dao.update(mapper.toEntity(task))
            }

        override suspend fun delete(task: Task) =
            withContext(dispatchers.io) {
                dao.delete(mapper.toEntity(task))
            }
    }
}