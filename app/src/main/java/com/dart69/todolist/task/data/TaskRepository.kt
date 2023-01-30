package com.dart69.todolist.task.data

import com.dart69.todolist.core.coroutines.*
import com.dart69.todolist.task.model.SearchingParams
import com.dart69.todolist.task.model.Task
import com.dart69.todolist.task.model.TasksRepository
import javax.inject.Inject
import javax.inject.Singleton

//Must be scoped in TaskViewModel!!!
@Singleton
class TasksRepositoryImpl @Inject constructor(
    private val dataSource: TasksLocalDataSource,
    private val searcherBuilder: VariadicSearcherBuilder<Results<List<@JvmSuppressWildcards Task>>, SearchingParams>,
) : TasksRepository {
    //late init to omit first search with empty query
    private lateinit var searcher: DebounceSearcher<Results<List<Task>>, SearchingParams>

    override fun searchBy(params: SearchingParams) {
        if (!this::searcher.isInitialized) {
            searcher = searcherBuilder
                .setInitialQuery(params)
                .setDebounceTime(0L)
                .setDataSource {
                    resultsFlowOf {
                        when (it) {
                            is SearchingParams.LoadAll -> dataSource.loadAll()
                            is SearchingParams.ByName -> dataSource.findByListName(it.name)
                            is SearchingParams.ByImportant -> dataSource.findByImportant(it.isImportant)
                        }
                    }
                }.build()
        } else {
            searcher.search(params)
        }
    }

    override fun observe(): ResultsFlow<List<Task>> =
        searcher.observe()

    override suspend fun createNewTask(task: Task) {
        searcher.withResearch {
            dataSource.insert(task)
        }
    }

    override suspend fun toggleCompleted(task: Task) {
        searcher.withResearch {
            dataSource.update(task.copy(isCompleted = !task.isCompleted))
        }
    }

    override suspend fun deleteTask(task: Task) {
        searcher.withResearch {
            dataSource.delete(task)
        }
    }

    override suspend fun toggleImportant(task: Task) {
        searcher.withResearch {
            dataSource.update(task.copy(isImportant = !task.isImportant))
        }
    }
}