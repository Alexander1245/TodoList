package com.dart69.todolist.greetings.di

import com.dart69.todolist.greetings.data.AppSettingsRepositoryImpl
import com.dart69.todolist.greetings.data.LocalAppSettingsDataSource
import com.dart69.todolist.greetings.data.LocalAppSettingsDataSourceImpl
import com.dart69.todolist.greetings.domain.AppSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GreetingsModule {

    @Binds
    @Singleton
    fun bindAppSettingRepository(
        repository: AppSettingsRepositoryImpl
    ): AppSettingsRepository

    @Binds
    @Singleton
    fun bindLocalAppSettingsDataSource(
        localAppSettingsDataSourceImpl: LocalAppSettingsDataSourceImpl
    ): LocalAppSettingsDataSource
}