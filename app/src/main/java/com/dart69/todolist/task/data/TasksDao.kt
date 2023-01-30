package com.dart69.todolist.task.data

import androidx.room.*

@Dao
interface TasksDao {

    @Query("SELECT * FROM TaskEntity WHERE listName = :listName")
    suspend fun loadByListName(listName: String): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity")
    suspend fun loadAll(): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE isImportant = :isImportant")
    suspend fun loadByImportant(isImportant: Boolean): List<TaskEntity>

    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)
}