package com.dart69.todolist.task.model

import android.os.Parcelable
import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model
import com.dart69.todolist.home.domain.model.TaskList
import com.dart69.todolist.task.domain.AvailableDates
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Long,
    val name: String,
    val note: String,
    val listName: String,
    val dueDate: AvailableDates?,
    val isImportant: Boolean,
    val isCompleted: Boolean,
) : Model, Identifiable<Long>, Parcelable {
    constructor(name: String, listName: String, isCompleted: Boolean) : this(
        id = 0L,
        name = name,
        note = "",
        listName = listName,
        dueDate = null,
        isImportant = listName == TaskList.Important.name,
        isCompleted = isCompleted,
    )

    override fun requireIdentifier(): Long = id

    companion object {
        val DEFAULT = Task("", "", false)
    }
}
