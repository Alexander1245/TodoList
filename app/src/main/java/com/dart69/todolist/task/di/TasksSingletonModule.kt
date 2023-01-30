package com.dart69.todolist.task.di

import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.VariadicSearcherBuilder
import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.task.data.TaskEntity
import com.dart69.todolist.task.data.TaskModelMapper
import com.dart69.todolist.task.data.TasksLocalDataSource
import com.dart69.todolist.task.data.TasksRepositoryImpl
import com.dart69.todolist.task.model.SearchingParams
import com.dart69.todolist.task.model.Task
import com.dart69.todolist.task.model.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TasksSingletonModule {

    @Binds
    @Singleton
    fun bindLocalDataSource(
        dataSource: TasksLocalDataSource.Default
    ): TasksLocalDataSource

    @Binds
    fun bindMapper(
        mapper: TaskModelMapper
    ): BidirectionalMapper<TaskEntity, Task>

    @Binds
    fun bindSearcher(
        builder: VariadicSearcherBuilder.Default<Results<List<@JvmSuppressWildcards Task>>, SearchingParams>
    ): VariadicSearcherBuilder<Results<List<@JvmSuppressWildcards Task>>, SearchingParams>

    @Binds
    @Singleton
    fun bindRepository(
        repository: TasksRepositoryImpl
    ): TasksRepository
}