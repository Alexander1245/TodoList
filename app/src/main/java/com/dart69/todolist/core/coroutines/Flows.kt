package com.dart69.todolist.core.coroutines

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
fun <T1, T2, R> Flow<T1>.combineFlatten(
    other: Flow<T2>,
    concurrency: Int = DEFAULT_CONCURRENCY,
    mapper: suspend (T1, T2) -> Flow<R>
): Flow<R> =
    this.combine(other) { t1, t2 ->
        mapper(t1, t2)
    }.flattenMerge(concurrency)