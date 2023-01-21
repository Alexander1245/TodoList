package com.dart69.todolist.core.coroutines

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

interface Searcher<T> {
    /**
     * return hot-flow with searched results
     * */
    fun observe(): Flow<T>

    /**
     * emit search query to the observer only if debounce timeout is completed.
     * duplicated queries ignored.
     * */
    fun search(query: String)

    /**
     * always emit search query, duplicates allowed
     * */
    fun forceSearch(query: String)

    /**
     * re-emit last query, duplicates allowed
     * */
    fun forceResearch()
}

@Suppress("FunctionName")
fun <T> Searcher(
    initial: String,
    debounce: Long,
    dataSource: suspend (String) -> Flow<T>
): Searcher<T> = SearcherImpl(initial, debounce, dataSource)

private data class SearchQuery(
    val query: String,
    val trigger: Any,
)

@OptIn(FlowPreview::class)
private class SearcherImpl<T>(
    initial: String,
    debounce: Long,
    dataSource: suspend (String) -> Flow<T>,
) : Searcher<T> {
    private val lastQuery = MutableStateFlow(SearchQuery(initial, Any()))
    private val observer = lastQuery
        .debounce(debounce)
        .distinctUntilChanged()
        .flatMapConcat { (query, _) -> dataSource(query) }

    override fun observe(): Flow<T> = observer

    override fun search(query: String) {
        lastQuery.update { it.copy(query = query) }
    }

    override fun forceSearch(query: String) {
        lastQuery.value = SearchQuery(query, Any())
    }

    override fun forceResearch() {
        lastQuery.update { it.copy(trigger = Any()) }
    }
}