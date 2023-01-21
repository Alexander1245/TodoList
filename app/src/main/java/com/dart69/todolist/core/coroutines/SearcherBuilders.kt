package com.dart69.todolist.core.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

interface SearcherBuilder<T> {
    fun setDataSource(source: suspend (String) -> Flow<T>): SearcherBuilder<T>

    fun build(): Searcher<T>
}

interface VariadicSearcherBuilder<T> : SearcherBuilder<T> {
    override fun setDataSource(source: suspend (String) -> Flow<T>): VariadicSearcherBuilder<T>

    fun setInitialQuery(query: String): VariadicSearcherBuilder<T>

    fun setDebouncePeriod(period: Long): VariadicSearcherBuilder<T>

    class Default<T> @Inject constructor() : VariadicSearcherBuilder<T> {
        private var initialQuery = ""
        private var debouncePeriod = 500L
        private var dataSource: suspend (String) -> Flow<T> = { emptyFlow() }

        override fun setInitialQuery(query: String): VariadicSearcherBuilder<T> =
            apply {
                initialQuery = query
            }

        override fun setDebouncePeriod(period: Long): VariadicSearcherBuilder<T> =
            apply {
                debouncePeriod = period
            }

        override fun setDataSource(source: suspend (String) -> Flow<T>): VariadicSearcherBuilder<T> =
            apply {
                dataSource = source
            }

        override fun build(): Searcher<T> =
            Searcher(initialQuery, debouncePeriod, dataSource)
    }
}