package com.dart69.todolist.home.data

import com.BaseTest
import com.assertCollectionsEquals
import com.dart69.TestDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.home.data.entity.TaskListEntity
import com.dart69.todolist.home.data.mapper.TaskListMapper
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class FakeTaskListDao : TaskListDao {
    private val data = mutableListOf<TaskListEntity>()

    suspend fun searchBy(query: String): List<TaskListEntity> =
        data.filter { query in it.name }

    override suspend fun loadAll(): List<TaskListEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(taskList: TaskListEntity) {
        data += taskList
    }

    override suspend fun upsert(taskLists: List<TaskListEntity>) {
        TODO("Not yet implemented")
    }
}

@ExperimentalCoroutinesApi
internal class TaskListRepositoryImplTest : BaseTest.Default() {
    private lateinit var repository: TaskListRepositoryImpl
    private lateinit var dao: FakeTaskListDao
    private lateinit var mapper: TaskListMapper

    private val predefinedItems = TaskList.PREDEFINED

    @Before
    override fun beforeEach() {
        dao = FakeTaskListDao()
        mapper = TaskListMapper()
        repository = TaskListRepositoryImpl(
            dispatchers = TestDispatchers(),
            mapper = mapper,
            dao = dao,
            predefinedTaskList = predefinedItems,
            queryMapper = { it },
        )
    }

    @Test
    fun `first observe returns success results with empty data`() = runTest {
        assertTrue(repository.observeTaskLists().value == Results.Success(emptyList<TaskList>()))
    }

    @Test
    fun `emitSearchQuery() emit results to repository observer`() = runBlocking {
        val tagName1 = "sss"
        val tagName2 = "ssd"
        val query = "ss"

        dao.insert(TaskListEntity(name = tagName1))
        dao.insert(TaskListEntity(name = tagName2))

        val expectedResults = listOf(
            Results.Success(emptyList()),
            Results.Loading(),
            Results.Success(dao.searchBy(query).map(mapper::toModel)),
        )
        val actualResults = mutableListOf<Results<List<TaskList>>>()

        val job = launch(UnconfinedTestDispatcher()) {
            repository.observeTaskLists().take(expectedResults.size).toList(actualResults)
        }

        repository.emitSearchQuery(query)
        job.join()
        assertCollectionsEquals(expectedResults, actualResults)
    }

    @Test
    fun `createNewList() creates new list and emit new results to observer`() = runBlocking {
        val newList = TaskList("test")

        val expectedResults = listOf(
            Results.Success(emptyList()),
            Results.Loading(),
            Results.Success(listOf(newList))
        )
        val actualResults = mutableListOf<Results<List<TaskList>>>()

        val job = launch(UnconfinedTestDispatcher()) {
            repository.observeTaskLists().take(expectedResults.size).toList(actualResults)
        }

        repository.createNewList(newList)
        job.join()
        assertCollectionsEquals(expectedResults, actualResults)
    }

    @Test
    fun `createNewList() must updates current observer if newList matches query`() = runBlocking {
        val initialList = TaskList("tag_1")
        val newList = TaskList("tag_2")

        dao.insert(mapper.toEntity(initialList))

        val expectedResults = listOf(
            Results.Success(emptyList()),
            Results.Loading(),
            Results.Success(listOf(initialList)),
            Results.Loading(),
            Results.Success(listOf(initialList, newList))
        )
        val actualResults = mutableListOf<Results<List<TaskList>>>()

        val job = launch(UnconfinedTestDispatcher()) {
            repository.observeTaskLists().take(expectedResults.size).toList(actualResults)
        }

        repository.emitSearchQuery("tag")
        repository.createNewList(newList)
        job.join()
        assertCollectionsEquals(expectedResults, actualResults)
    }

    @Test
    fun `createSmartLists() creates Important and Tasks lists`() = runBlocking {
        val expectedStates = listOf(
            Results.Success(emptyList()),
            Results.Loading(),
            Results.Success(predefinedItems),
        )
        val actualStates = mutableListOf<Results<List<TaskList>>>()
        val job = launch(UnconfinedTestDispatcher()) {
            repository.observeTaskLists().take(expectedStates.size).collect {
                actualStates += it
            }
        }
        repository.createSmartLists()
        job.join()
        assertCollectionsEquals(expectedStates, actualStates)
    }
}