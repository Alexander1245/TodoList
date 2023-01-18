package com.dart69.todolist.home.data.test

import com.BaseTest
import com.assertCollectionsEquals
import com.dart69.TestDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.home.data.*
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

class FakeLocalDataSource : LocalTaskListDataSource {
    private val mutex = Mutex()
    private val data = mutableListOf<TaskList>()

    override suspend fun loadAll(): List<TaskList> = data

    override suspend fun insert(taskList: TaskList) = mutex.withLock {
        data += taskList
    }

    override suspend fun upsert(taskLists: List<TaskList>) {
        if(!data.containsAll(taskLists)) {
            mutex.withLock {
                data += taskLists
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class NewTaskListRepositoryTest : BaseTest.Default() {
    private lateinit var repository: TaskListRepositoryImpl

    @Before
    override fun beforeEach() {
        val dispatchers = TestDispatchers()
        repository = TaskListRepositoryImpl(
            cachedDataSource = CachedTaskListDataSource.Default(dispatchers),
            localDataSource = FakeLocalDataSource(),
            smartLists = TaskList.PREDEFINED,
        )
    }

    @Test
    fun `initialize upsert predefined items and notifies observer`() = runBlocking {
        val expected = listOf(
            Results.Success(emptyList()),
            Results.Loading(),
            Results.Success(TaskList.PREDEFINED)
        )
        val actual = async (UnconfinedTestDispatcher()){
            repository.observe().take(expected.size).toList()
        }
        repository.initializeSmartLists()
        assertCollectionsEquals(expected, actual.await())
    }

    @Test
    fun `searchBy find all items matches given query`() = runBlocking {
        `initialize upsert predefined items and notifies observer`()
        val expected = listOf(
            Results.Loading(),
            Results.Success(listOf(TaskList.Important))
        )
        val actual = async(UnconfinedTestDispatcher()) {
            repository.observe().drop(1).take(expected.size).toList()
        }
        repository.search("imp")
        assertCollectionsEquals(expected, actual.await())
    }

    @Test
    fun `createNewList creates new list and notifies observer`() = runBlocking {
        `searchBy find all items matches given query`()
        val expected = listOf(
            Results.Loading(),
            Results.Success(listOf(TaskList.Important, TaskList("Implement")))
        )
        val actual = async(UnconfinedTestDispatcher()) {
            repository.observe().drop(1).take(expected.size).toList()
        }
        repository.createNewList(TaskList("Implement"))
        assertCollectionsEquals(expected, actual.await())
    }

    @Test
    fun `isNameAvailable return true if name is valid`() = runBlocking {
        val actual = async {
            repository.isNameAvailable("cat").toList()
        }
        val expected = listOf(
            Results.Loading(),
            Results.Success(true)
        )
        assertCollectionsEquals(expected, actual.await())
    }

    @Test
    fun `isNameAvailable return NotUniqueNameException if name already exists`() = runBlocking {
        repository.createNewList(TaskList("cat"))
        val actual = async {
            repository.isNameAvailable("cat").toList()
        }
        val expected = listOf(
            Results.Loading<Boolean>(),
            Results.Error(NotUniqueNameException()),
        )
        assertCollectionsEquals(expected, actual.await())
    }

    @Test
    fun `isNameAvailable return EmptyNameException if name is Blank`() = runBlocking {
        val actual = async {
            repository.isNameAvailable("").toList()
        }
        val expected = listOf(
            Results.Loading<Boolean>(),
            Results.Error(EmptyNameException()),
        )
        assertCollectionsEquals(expected, actual.await())
    }

    @Test
    fun `isNameAvailable return NameTooLongException if name size longer than 20`() = runBlocking {
        val actual = async {
            repository.isNameAvailable("a".repeat(21)).toList()
        }
        val expected = listOf(
            Results.Loading<Boolean>(),
            Results.Error(NameTooLongException()),
        )
        assertCollectionsEquals(expected, actual.await())
    }
}