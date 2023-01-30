package com.dart69.todolist.core.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.dart69.todolist.home.data.TaskListDao
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.task.data.TaskEntity
import com.dart69.todolist.task.data.TasksDao

@Database(
    entities = [TaskListEntity::class, TaskEntity::class],
    version = 6,
    autoMigrations = [
        AutoMigration(2, 3),
        AutoMigration(3, 4),
        AutoMigration(5, 6),
    ],
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao

    abstract fun tasksDao(): TasksDao

    companion object {
        val NAME = AppDataBase::class.simpleName
    }
}