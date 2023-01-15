package com.dart69.todolist.home.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.dart69.todolist.home.data.entity.TaskListEntity

@Dao
interface TaskListDao {

    @Query("SELECT * FROM TaskListEntity WHERE name LIKE :query")
    suspend fun searchBy(query: String): List<TaskListEntity>

    @Insert
    suspend fun insert(taskList: TaskListEntity)

    @Upsert
    suspend fun upsert(vararg taskLists: TaskListEntity)
}