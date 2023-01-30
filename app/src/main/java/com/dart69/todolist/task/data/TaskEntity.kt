package com.dart69.todolist.task.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dart69.todolist.core.data.EntityModel
import com.dart69.todolist.home.data.entity.TaskListEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TaskListEntity::class,
            parentColumns = ["name"],
            childColumns = ["listName"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    @ColumnInfo(index = true)
    val listName: String,
    val dueDate: String,
    val isImportant: Boolean,
    val isCompleted: Boolean,
): EntityModel