package com.dart69.todolist.home.di

import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.SearcherBuilder
import com.dart69.todolist.core.coroutines.VariadicSearcherBuilder
import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.home.data.NameParserImpl
import com.dart69.todolist.home.data.TaskListLocalDataSource
import com.dart69.todolist.home.data.TaskListRepositoryImpl
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.data.mapper.TaskListMapper
import com.dart69.todolist.home.domain.NameParser
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomeSingletonModule {

    @Binds
    fun bindMapper(
        mapper: TaskListMapper
    ): BidirectionalMapper<TaskListEntity, TaskList>

    @Binds
    fun bindParser(
        parser: NameParserImpl
    ): NameParser

    @Binds
    @Singleton
    fun bindLocalDataSource(
        dataSource: TaskListLocalDataSource.Default
    ): TaskListLocalDataSource

    @Binds
    @Singleton
    fun bindRepository(
        repository: TaskListRepositoryImpl
    ): TaskListRepository


    @Binds
    @Singleton
    fun bindSearcherBuilder(
        builder: VariadicSearcherBuilder.Default<Results<List<TaskList>>, String>
    ): SearcherBuilder<Results<List<TaskList>>, String>
}