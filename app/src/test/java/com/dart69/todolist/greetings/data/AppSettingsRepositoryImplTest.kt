package com.dart69.todolist.greetings.data

import com.BaseTest
import com.dart69.FakeDataStore
import com.dart69.TestDispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class AppSettingsRepositoryImplTest: BaseTest.Default() {
    private lateinit var repository: AppSettingsRepositoryImpl

    @Before
    override fun beforeEach() {
        val dataStore = FakeDataStore()
        val dispatchers = TestDispatchers()
        val dataSource = LocalAppSettingsDataSourceImpl(dataStore, dispatchers)
        repository = AppSettingsRepositoryImpl(dataSource)
    }

    @Test
    fun isAppFirstRun() = runBlocking {
        assertEquals(true, repository.isAppFirstRun())
    }

    @Test
    fun runAppFirstTime() = runBlocking {
        repeat(10) {
            repository.runAppFirstTime()
            assertEquals(false, repository.isAppFirstRun())
        }
    }
}