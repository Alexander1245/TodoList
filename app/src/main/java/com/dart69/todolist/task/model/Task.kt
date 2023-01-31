package com.dart69.todolist.task.model

import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model
import com.dart69.todolist.home.domain.model.TaskList

data class Task(
    val id: Long,
    val name: String,
    val listName: String,
    val dueDate: String,
    val isImportant: Boolean,
    val isCompleted: Boolean,
) : Model, Identifiable<Long> {
    constructor(name: String, listName: String, isCompleted: Boolean): this(
        id = 0L,
        name = name,
        listName = listName,
        dueDate = "",
        isImportant = listName == TaskList.Important.name,
        isCompleted = isCompleted,
    )

    override fun requireIdentifier(): Long = id
}
