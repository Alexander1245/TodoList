package com.dart69.todolist.greetings.data

import com.dart69.todolist.greetings.domain.AppSettings
import com.dart69.todolist.greetings.domain.AppSettingsRepository
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val localSource: LocalAppSettingsDataSource
) : AppSettingsRepository {

    override suspend fun isAppFirstRun(): Boolean = localSource.loadAppSettings().isAppFirstRun

    override suspend fun runAppFirstTime() =
        localSource.saveAppSettings(AppSettings(isAppFirstRun = false))
}