package com.dart69.todolist.greetings.domain

interface AppSettingsRepository {
    suspend fun isAppFirstRun(): Boolean

    suspend fun runAppFirstTime()
}