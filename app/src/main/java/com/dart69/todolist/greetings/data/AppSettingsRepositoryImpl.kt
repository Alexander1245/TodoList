package com.dart69.todolist.greetings.data

import com.dart69.todolist.greetings.domain.AppSettingsRepository

class AppSettingsRepositoryImpl(
    private val localSource: LocalAppSettingsDataSource
) : AppSettingsRepository {

    override suspend fun isAppFirstRun(): Boolean = localSource.loadAppSettings().isAppFirstRun
}