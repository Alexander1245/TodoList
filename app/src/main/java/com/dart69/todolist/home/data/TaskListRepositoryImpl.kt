package com.dart69.todolist.home.data

import com.dart69.todolist.core.coroutines.Results
import com.dart69.todolist.core.coroutines.ResultsFlow
import com.dart69.todolist.core.coroutines.SearcherBuilder
import com.dart69.todolist.core.coroutines.resultsFlowOf
import com.dart69.todolist.home.domain.TaskListRepository
import com.dart69.todolist.home.domain.model.TaskList
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    private val dataSource: TaskListLocalDataSource,
    searcherBuilder: SearcherBuilder<Results<List<TaskList>>, String>,
) : TaskListRepository {
    private val searcher = searcherBuilder.setDataSource {
        resultsFlowOf { dataSource.searchBy(it) }
    }.build()

    override fun observe(): ResultsFlow<List<TaskList>> = searcher.observe()

    override fun findTaskByName(name: String): ResultsFlow<TaskList?> = resultsFlowOf {
        dataSource.findByName(name)
    }

    override fun updateSearchQuery(query: String) {
        searcher.search(query)
    }

    override suspend fun createNewList(taskList: TaskList) {
        dataSource.insert(taskList)
        searcher.forceResearch()
    }

    override suspend fun deleteTaskListByName(name: String) {
        if(name in TaskList.PREDEFINED.map { it.name }) {
            error("Can't delete predefined task list.")
        }
        dataSource.deleteTaskListByName(name)
        searcher.forceResearch()
    }
}