package com.dart69.todolist.core.di

import com.dart69.todolist.core.coroutines.AppDispatchers
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Binds
    fun bindDispatchers(
        dispatchers: AppDispatchers
    ): AvailableDispatchers
}