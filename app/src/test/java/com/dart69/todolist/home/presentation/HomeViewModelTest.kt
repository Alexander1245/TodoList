package com.dart69.todolist.home.presentation

import com.BaseTest
import com.dart69.TestDispatchers
import com.dart69.todolist.core.coroutines.*
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FakeTaskListRepository : TaskListRepository {
    private val data = MutableResultsStateFlow<List<TaskList>>(Results.Success(emptyList()))

    override fun observeTaskLists(): ResultsStateFlow<List<TaskList>> = data.asStateFlow()

    override suspend fun emitSearchQuery(query: String) {
        data.emitResults { listOf(TaskList(query)) }
    }

    override suspend fun emitLastQuery() {

    }

    override suspend fun createNewList(list: TaskList) {
        data.emitResults { data.value.takeSuccess()!! + listOf(list) }
    }

    override suspend fun createSmartLists() {
        data.emitResults { data.value.takeSuccess()!! + TaskList.PREDEFINED }
    }

}

internal class HomeViewModelTest : BaseTest.Default() {
    private lateinit var viewModel: HomeViewModel

    @Before
    override fun beforeEach() {
        /*viewModel = HomeViewModel(
            repository = FakeTaskListRepository(),
            dispatchers = TestDispatchers(),

        )*/
    }

    @Test
    fun `viewModel creates predefined lists each time`() = runBlocking {

    }

    @Test
    fun search() {
    }

    @Test
    fun onQueryTextSubmit() {
    }

    @Test
    fun onQueryTextChange() {
    }

    @Test
    fun onAddNewButtonClick() {
    }
}