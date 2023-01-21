package com.dart69.todolist.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dart69.todolist.home.data.TaskListDao
import com.dart69.todolist.home.data.entity.TaskListEntity

@Database(entities = [TaskListEntity::class], version = 2)
abstract class AppDataBase : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao

    companion object {
        val NAME = AppDataBase::class.simpleName
    }
}