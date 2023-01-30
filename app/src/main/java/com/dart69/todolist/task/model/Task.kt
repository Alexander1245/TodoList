package com.dart69.todolist.task.model

import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model

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
        isImportant = false,
        isCompleted = isCompleted,
    )

    override fun requireIdentifier(): Long = id
}
