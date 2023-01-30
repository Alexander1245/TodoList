package com.dart69.todolist.task.data

import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.task.model.Task
import javax.inject.Inject

class TaskModelMapper @Inject constructor(): BidirectionalMapper<TaskEntity, Task> {
    override fun toModel(entity: TaskEntity): Task =
        Task(
            name = entity.name,
            id = entity.id,
            listName = entity.listName,
            dueDate = entity.dueDate,
            isImportant = entity.isImportant,
            isCompleted = entity.isCompleted,
        )

    override fun toEntity(model: Task): TaskEntity =
        TaskEntity(
            name = model.name,
            id = model.id,
            listName = model.listName,
            dueDate = model.dueDate,
            isImportant = model.isImportant,
            isCompleted = model.isCompleted,
        )
}