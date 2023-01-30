package com.dart69.todolist.home.data.mapper

import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.domain.model.TaskList
import javax.inject.Inject

class TaskListMapper @Inject constructor() : BidirectionalMapper<TaskListEntity, TaskList> {

    override fun toModel(entity: TaskListEntity): TaskList =
        when (entity.name) {
            TaskList.Tasks.name -> TaskList.Tasks
            TaskList.Important.name -> TaskList.Important
            else -> TaskList.UserDefined(entity.name)
        }

    override fun toEntity(model: TaskList): TaskListEntity =
        TaskListEntity(model.name)
}