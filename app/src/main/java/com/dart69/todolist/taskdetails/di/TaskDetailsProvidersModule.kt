package com.dart69.todolist.taskdetails.di

import com.dart69.todolist.task.domain.AvailableDates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskDetailsProvidersModule {

    @Provides
    @Singleton
    fun provideDateFactory(): AvailableDates.Factory = AvailableDates.Factory
}