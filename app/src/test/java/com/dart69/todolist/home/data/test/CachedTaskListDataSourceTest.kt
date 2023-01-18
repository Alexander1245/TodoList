package com.dart69.todolist.home.data.test

import com.BaseTest
import com.assertCollectionsEquals
import com.dart69.TestDispatchers
import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.home.data.CachedTaskListDataSource
import com.dart69.todolist.home.domain.model.TaskList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

//TODO: Rewrite!!!
internal class CachedTaskListDataSourceTest : BaseTest.Default() {
    private lateinit var dataSource: CachedTaskListDataSource.Default

    @Before
    override fun beforeEach() {
        dataSource = CachedTaskListDataSource.Default(TestDispatchers())
    }

    @Test
    fun searchBy() = runBlocking {
        cache()
        val expectedList = listOf(
            Results.Loading(),
            Results.Success(listOf(TaskList("baobab"))),
        )
        val actualList = mutableListOf<Results<List<TaskList>>>()
        launch {
            /*dataSource.searchBy("bAb").toList(actualList)*/
        }.join()
        assertCollectionsEquals(expectedList, actualList)
    }

    @Test
    fun cache() = runBlocking {
        val list = listOf(
            TaskList("article"),
            TaskList("baobab"),
            TaskList("cat"),
        )
        dataSource.cache(list)
        val expectedList = listOf(
            Results.Loading(),
            Results.Success(list),
        )
        val actualList = mutableListOf<Results<List<TaskList>>>()
        launch {
            /*dataSource.searchBy("").toList(actualList)*/
        }.join()
        assertCollectionsEquals(expectedList, actualList)
    }
}