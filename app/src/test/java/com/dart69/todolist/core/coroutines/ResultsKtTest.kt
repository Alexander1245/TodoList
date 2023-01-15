package com.dart69.todolist.core.coroutines

import com.assertCollectionsEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ResultsKtTest {

    @Test
    fun resultsFlowOf() = runBlocking {
        val expected = listOf(
            Results.Loading(),
            Results.Success(12),
        )
        val actual = mutableListOf<Results<Int>>()
        val job = launch {
            resultsFlowOf {
                12
            }.take(expected.size).toList(actual)
        }
        job.join()
        assertCollectionsEquals(expected, actual)
    }

    @Test
    fun resultsFlowOfError() = runBlocking {
        val expectedThrowable = Exception("expected")
        val expected = listOf<Results<Int>>(
            Results.Loading(),
            Results.Error(expectedThrowable),
        )
        val actual = mutableListOf<Results<Int>>()
        val job = launch {
            resultsFlowOf<Int> {
                throw expectedThrowable
            }.take(expected.size).toList(actual)
        }
        job.join()
        assertCollectionsEquals(expected, actual)
    }

    @Test
    fun emitResults() = runBlocking {
        val flow = MutableResultsStateFlow<Int>(Results.Loading())
        val expected = listOf(
            Results.Loading(),
            Results.Success(12),
            Results.Loading(),
            Results.Success(13),
        )
        val actual = mutableListOf<Results<Int>>()
        val job = launch(UnconfinedTestDispatcher()) {
            flow.take(expected.size).toList(actual)
        }
        flow.emitResults { 12 }
        flow.emitResults { 13 }
        job.join()
        assertCollectionsEquals(expected, actual)
    }
}