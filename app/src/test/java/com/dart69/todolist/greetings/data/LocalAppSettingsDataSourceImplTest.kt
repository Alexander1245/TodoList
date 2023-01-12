package com.dart69.todolist.greetings.data

import com.BaseTest
import com.dart69.FakeDataStore
import com.dart69.TestDispatchers
import com.dart69.todolist.greetings.domain.AppSettings
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class LocalAppSettingsDataSourceImplTest : BaseTest.Default() {
    private val prefsKey = "is_app_first_run"
    private lateinit var dataStore: FakeDataStore
    private lateinit var dataSource: LocalAppSettingsDataSourceImpl

    @Before
    override fun beforeEach() {
        val dispatchers = TestDispatchers()
        dataStore = FakeDataStore()
        dataSource = LocalAppSettingsDataSourceImpl(dataStore, dispatchers)
    }

    @Test
    fun loadAppSettingsFirstTime() = runBlocking {
        val settings = dataSource.loadAppSettings()
        val expected = AppSettings(isAppFirstRun = true)
        assertEquals(expected, settings)
    }

    @Test
    fun loadAppSettings() = runBlocking {
        dataStore.setInitialSettings(prefsKey, AppSettings(isAppFirstRun = false))
        val expected = AppSettings(isAppFirstRun = false)
        val actual = dataSource.loadAppSettings()
        assertEquals(expected, actual)
    }

    @Test
    fun saveAppSettingsFirstTime() = runBlocking {
        dataSource.saveAppSettings(AppSettings(isAppFirstRun = false))
        val expected = AppSettings(isAppFirstRun = false)
        val actual = dataSource.loadAppSettings()
        assertEquals(expected, actual)
    }

    @Test
    fun saveAppSettings() = runBlocking {
        dataStore.setInitialSettings(prefsKey, AppSettings(isAppFirstRun = false))
        dataSource.saveAppSettings(AppSettings(isAppFirstRun = true))
        val expected = AppSettings(isAppFirstRun = true)
        val actual = dataSource.loadAppSettings()
        assertEquals(expected, actual)
    }
}