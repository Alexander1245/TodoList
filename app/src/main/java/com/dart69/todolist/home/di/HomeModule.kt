package com.dart69.todolist.home.di

import com.dart69.todolist.core.data.mapper.BidirectionalMapper
import com.dart69.todolist.home.data.QueryMapper
import com.dart69.todolist.home.data.RoomQueryMapper
import com.dart69.todolist.home.data.TaskListRepositoryImpl
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.data.mapper.TaskListMapper
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    fun bindMapper(
        mapper: TaskListMapper
    ): BidirectionalMapper<TaskListEntity, TaskList>

    @Binds
    fun bindQueryMapper(
        mapper: RoomQueryMapper
    ): QueryMapper

    @Binds
    @Singleton
    fun bindRepository(
        repository: TaskListRepositoryImpl
    ): TaskListRepository
}