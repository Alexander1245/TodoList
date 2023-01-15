package com.dart69.todolist.core.coroutines

import kotlinx.coroutines.flow.*

typealias ResultsFlow<T> = Flow<Results<T>>
typealias MutableResultsStateFlow<T> = MutableStateFlow<Results<T>>
typealias ResultsStateFlow<T> = StateFlow<Results<T>>

sealed class Results<T> {
    data class Loading<T>(val inProgress: Boolean = true) : Results<T>()

    data class Error<T>(val throwable: Throwable) : Results<T>()

    data class Success<T>(val data: T) : Results<T>()
}

fun <T> resultsFlowOf(block: suspend () -> T): ResultsFlow<T> = flow<Results<T>> {
    emit(Results.Success(block()))
}.onStart {
    emit(Results.Loading())
}.catch {
    emit(Results.Error(it))
}

@Suppress("FunctionName")
fun <T> MutableResultsStateFlow(results: Results<T>): MutableResultsStateFlow<T> =
    MutableStateFlow(results)

suspend fun <T> MutableResultsStateFlow<T>.emitResults(block: suspend () -> T) {
    emitAll(resultsFlowOf(block))
}

fun <T> Results<T>.takeSuccess(): T? = if(this is Results.Success) data else null