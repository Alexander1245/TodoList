package com.dart69.todolist.home.di

import com.dart69.todolist.core.coroutines.VariadicSearcherBuilder
import com.dart69.todolist.core.coroutines.SearcherBuilder
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.core.presentation.communication.Communicator
import com.dart69.todolist.core.presentation.communication.Receiver
import com.dart69.todolist.core.presentation.communication.Sender
import com.dart69.todolist.home.data.NameParserImpl
import com.dart69.todolist.home.data.TaskListRepositoryImpl
import com.dart69.todolist.home.data.TasksLocalDataSource
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.data.mapper.TaskListMapper
import com.dart69.todolist.home.domain.NameParser
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import com.dart69.todolist.home.presentation.TaskListCommunicator
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
        dataSource: TasksLocalDataSource.Default
    ): TasksLocalDataSource

    @Binds
    @Singleton
    fun bindRepository(
        repository: TaskListRepositoryImpl
    ): TaskListRepository

    @Binds
    @Singleton
    fun bindCommunicator(
        communicator: TaskListCommunicator
    ): Communicator<TaskList>

    @Binds
    @Singleton
    fun bindReceiver(
        communicator: Communicator<TaskList>
    ): Receiver<TaskList>

    @Binds
    @Singleton
    fun bindSender(
        communicator: Communicator<TaskList>
    ): Sender<TaskList>

    @Binds
    @Singleton
    fun bindSearcherBuilder(
        builder: VariadicSearcherBuilder.Default<Results<List<TaskList>>>
    ): SearcherBuilder<Results<List<TaskList>>>
}