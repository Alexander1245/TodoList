package com.dart69.todolist.home.data

import androidx.room.*
import com.dart69.todolist.home.data.entity.TaskListEntity

@Dao
interface TaskListDao {
    @Query("SELECT * FROM TaskListEntity WHERE name LIKE :query")
    suspend fun searchBy(query: String): List<TaskListEntity>

    @Query("SELECT * FROM TaskListEntity WHERE NAME = :name")
    suspend fun findByName(name: String): TaskListEntity?

    @Insert
    suspend fun insert(taskLists: TaskListEntity)

    @Upsert
    suspend fun upsert(taskLists: List<TaskListEntity>)

    @Query("DELETE FROM TaskListEntity WHERE name = :name")
    suspend fun deleteListByName(name: String)
}