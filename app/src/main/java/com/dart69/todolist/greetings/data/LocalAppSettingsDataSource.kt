package com.dart69.todolist.greetings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.greetings.domain.AppSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LocalAppSettingsDataSource {
    suspend fun loadAppSettings(): AppSettings

    suspend fun saveAppSettings(settings: AppSettings)
}

class LocalAppSettingsDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dispatchers: AvailableDispatchers,
) : LocalAppSettingsDataSource {

    override suspend fun loadAppSettings(): AppSettings = withContext(dispatchers.io) {
        val isAppFirstRun = dataStore.data.map { it[KEY] ?: true }.first()
        AppSettings(isAppFirstRun)
    }

    override suspend fun saveAppSettings(settings: AppSettings) =
        withContext<Unit>(dispatchers.io) {
            dataStore.edit { preferences ->
                preferences[KEY] = settings.isAppFirstRun
            }
        }

    private companion object {
        const val IS_APP_FIRST_RUN = "is_app_first_run"
        val KEY = booleanPreferencesKey(IS_APP_FIRST_RUN)
    }
}