package com.dart69.todolist.core.coroutines

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlin.experimental.ExperimentalTypeInference

/**
 * @param [K] -> query type
 * */
interface DebounceSearcher<T, K> {
    /**
     * return hot-flow with searched results
     * */
    fun observe(): Flow<T>

    /**
     * emit search query to the observer only if debounce timeout is completed.
     * duplicated queries ignored.
     * */
    fun search(query: K)

    /**
     * always emit search query, duplicates allowed
     * */
    fun forceSearch(query: K)

    /**
     * re-emit last query, duplicates allowed
     * */
    fun forceResearch()
}

suspend fun DebounceSearcher<*, *>.withResearch(block: suspend () -> Unit) {
    block()
    forceResearch()
}

@Suppress("FunctionName")
fun <T, K> CommonSearcher(
    initial: K,
    debounce: Long,
    dataSource: suspend (K) -> Flow<T>
): DebounceSearcher<T, K> = SearcherImpl(initial, debounce, dataSource)

private data class SearchQuery<K>(
    val query: K,
    val trigger: Any,
)

//TODO: Add forceOnChanged, which called each time when 'lastQuery' changed.
@OptIn(FlowPreview::class)
private class SearcherImpl<T, K>(
    initial: K,
    debounce: Long,
    dataSource: suspend (K) -> Flow<T>,
) : DebounceSearcher<T, K> {
    private val lastQuery = MutableStateFlow(SearchQuery(initial, Any()))

    private val observer = lastQuery
        .debounce(debounce)
        .distinctUntilChanged()
        .flatMapConcat { (query, _) -> dataSource(query) }

    override fun observe(): Flow<T> = observer

    override fun search(query: K) {
        lastQuery.update { it.copy(query = query) }
    }

    override fun forceSearch(query: K) {
        lastQuery.value = SearchQuery(query, Any())
    }

    override fun forceResearch() {
        lastQuery.update { it.copy(trigger = Any()) }
    }
}
