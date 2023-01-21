@file:OptIn(ExperimentalCoroutinesApi::class)

package com.dart69.todolist.core.coroutines

import com.BaseTest
import com.assertCollectionsEquals
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

internal class TestDataSource {
    private val data = mutableListOf<TaskList>()

    fun insert(taskList: TaskList) {
        data += taskList
    }

    fun search(query: String): Flow<List<TaskList>> = flow {
        emit(data.filter { it.name.contains(query, true) })
    }
}

internal class ForceSearcherImplTest : BaseTest.Default() {
    private lateinit var searcher: Searcher<List<TaskList>>
    private lateinit var dataSource: TestDataSource
    private lateinit var data: List<TaskList>

    @Before
    override fun beforeEach() {
        dataSource = TestDataSource()
        searcher = Searcher(debounce = 0L, initial = "", dataSource = dataSource::search)
        data = List(10) {
            TaskList("Task $it")
        }.onEach(dataSource::insert)
    }

    @Test
    fun `search emit only unique queries`() = runBlocking {
        val expected = listOf(data)
        val actual = mutableListOf<List<TaskList>>()
        val job = launch(UnconfinedTestDispatcher()) {
            searcher.observe().take(1).toList(actual)
        }
        repeat(3) {
            searcher.search("")
        }
        job.join()
        assertCollectionsEquals(expected, actual)
    }

    @Test
    fun forceSearch() = runBlocking {
        val expected = listOf(
            data,
            data,
            data,
            data,
        )
        val actual = mutableListOf<List<TaskList>>()
        val job = launch(UnconfinedTestDispatcher()) {
            searcher.observe().take(expected.size).toList(actual)
        }
        repeat(expected.size - 1) {
            searcher.forceSearch("")
        }
        job.join()
        assertCollectionsEquals(expected, actual)
    }
}