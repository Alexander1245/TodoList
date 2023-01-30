package com.dart69.todolist.core.coroutines

import com.dart69.todolist.core.di.InitialQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

interface SearcherBuilder<T, K> {
    fun setDataSource(source: suspend (K) -> Flow<T>): SearcherBuilder<T, K>

    fun build(): DebounceSearcher<T, K>
}

interface VariadicSearcherBuilder<T, K> : SearcherBuilder<T, K> {
    fun setInitialQuery(initialQuery: K): VariadicSearcherBuilder<T, K>

    fun setDebounceTime(mills: Long): VariadicSearcherBuilder<T, K>

    override fun setDataSource(source: suspend (K) -> Flow<T>): VariadicSearcherBuilder<T, K>

    class Default<T, K> @Inject constructor(
        @InitialQuery initial: K
    ) : VariadicSearcherBuilder<T, K> {
        private var initialQuery: K = initial
        private var debouncePeriod = 500L
        private var dataSource: suspend (K) -> Flow<T> = { emptyFlow() }

        override fun setInitialQuery(initialQuery: K): VariadicSearcherBuilder<T, K> =
            apply {
                this.initialQuery = initialQuery
            }

        override fun setDebounceTime(mills: Long): VariadicSearcherBuilder<T, K> =
            apply {
                debouncePeriod = mills
            }

        override fun setDataSource(source: suspend (K) -> Flow<T>): VariadicSearcherBuilder<T, K> =
            apply {
                dataSource = source
            }

        override fun build(): DebounceSearcher<T, K> =
            CommonSearcher(initialQuery, debouncePeriod, dataSource)
    }
}