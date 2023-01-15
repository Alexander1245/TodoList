package com.dart69.todolist.greetings.presentation

import com.BaseTest
import com.assertCollectionsEquals
import com.dart69.TestDispatchers
import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.NavigationEvent
import com.dart69.todolist.splash.domain.usecase.RunAppFirstTimeUseCase
import com.dart69.todolist.splash.presentation.FakeDirections
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class FakeRunAppFirstTimeUseCase : RunAppFirstTimeUseCase {
    var isAppFirstRun = false
        private set

    override suspend fun invoke() {
        isAppFirstRun = true
    }
}

@ExperimentalCoroutinesApi
internal class GreetingsViewModelTest : BaseTest.Default() {
    private lateinit var viewModel: GreetingsViewModel
    private lateinit var useCase: FakeRunAppFirstTimeUseCase
    private val testScope = TestScope()

    @Before
    override fun beforeEach() {
        useCase = FakeRunAppFirstTimeUseCase()
        viewModel = GreetingsViewModel(
            dispatchers = TestDispatchers(),
            home = FakeDirections(11),
            runAppFirstTimeUseCase = useCase,
            applicationScope = testScope,
        )
    }

    @Test
    fun getStarted() = testScope.runTest {
        assertFalse(useCase.isAppFirstRun)

        val expectedScreenStates = listOf(
            GreetingsScreenState(R.drawable.welcome_uno, R.string.greetings, R.string.hint),
        )
        val actualScreenStates = mutableListOf<GreetingsScreenState>()

        val expectedEvents = listOf(NavigationEvent(FakeDirections(11)))
        val actualEvents = mutableListOf<NavigationEvent>()

        val job1 = launch(UnconfinedTestDispatcher()) {
            viewModel.observeScreenState().take(expectedScreenStates.size).toList(actualScreenStates)
        }
        val job2 = launch(UnconfinedTestDispatcher()) {
            viewModel.observeEvent().take(expectedEvents.size).toList(actualEvents)
        }

        viewModel.getStarted()
        joinAll(job1, job2)

        assertCollectionsEquals(expectedScreenStates, actualScreenStates)
        assertCollectionsEquals(expectedEvents, actualEvents)
        assertTrue(useCase.isAppFirstRun)
    }
}