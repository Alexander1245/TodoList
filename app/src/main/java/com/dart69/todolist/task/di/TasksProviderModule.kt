package com.dart69.todolist.task.di

import com.dart69.todolist.core.data.database.AppDataBase
import com.dart69.todolist.core.di.InitialQuery
import com.dart69.todolist.task.data.TasksDao
import com.dart69.todolist.task.model.SearchingParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasksProviderModule {

    @Provides
    @Singleton
    fun provideDao(appDataBase: AppDataBase): TasksDao =
        appDataBase.tasksDao()

    @Provides
    @InitialQuery
    fun provideInitialQuery(): Long = 0L

    @Provides
    @InitialQuery
    fun provideInitialSearch(): SearchingParams = SearchingParams.LoadAll
}