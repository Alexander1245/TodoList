package com.dart69

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.dart69.todolist.greetings.domain.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataStore() : DataStore<Preferences> {
    private var fakeData: MutablePreferences = mutablePreferencesOf()

    fun setInitialSettings(key: String, appSettings: AppSettings) {
        fakeData[booleanPreferencesKey(key)] = appSettings.isAppFirstRun
    }

    override val data: Flow<Preferences> get() = flowOf(fakeData)

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        fakeData = transform(fakeData).toMutablePreferences()
        return fakeData
    }
}