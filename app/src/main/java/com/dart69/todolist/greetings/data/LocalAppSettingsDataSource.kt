package com.dart69.todolist.greetings.data

import androidx.datastore.core.DataStore
import com.dart69.todolist.greetings.domain.AppSettings
import kotlinx.coroutines.flow.first

interface LocalAppSettingsDataSource {
    suspend fun loadAppSettings(): AppSettings
}

class LocalAppSettingsDataSourceImpl(
    private val dataStore: DataStore<AppSettings>,
) : LocalAppSettingsDataSource {

    override suspend fun loadAppSettings(): AppSettings = dataStore.data.first()

    private companion object {
        const val IS_APP_FIRST_RUN = "is_app_first_run"
    }
}