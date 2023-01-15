package com.dart69.todolist.home.di

import com.dart69.todolist.core.data.database.AppDataBase
import com.dart69.todolist.home.data.TaskListDao
import com.dart69.todolist.home.domain.model.TaskList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Predefined

@Module
@InstallIn(SingletonComponent::class)
object HomeProvidersModule {

    @Provides
    @Singleton
    fun provideTodoListDao(
        dataBase: AppDataBase
    ): TaskListDao = dataBase.taskListDao()

    @Provides
    @Predefined
    fun providePredefinedTasks(): List<@JvmSuppressWildcards TaskList> = TaskList.PREDEFINED
}