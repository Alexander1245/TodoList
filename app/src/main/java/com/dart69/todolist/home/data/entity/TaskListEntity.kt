package com.dart69.todolist.home.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dart69.todolist.core.data.EntityModel

@Entity
data class TaskListEntity(
    @PrimaryKey val name: String,
) : EntityModel
