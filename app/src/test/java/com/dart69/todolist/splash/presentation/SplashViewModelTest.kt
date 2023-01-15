@file:OptIn(ExperimentalCoroutinesApi::class)

package com.dart69.todolist.splash.presentation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.BaseTest
import com.assertCollectionsEquals
import com.dart69.TestDispatchers
import com.dart69.todolist.core.presentation.NavigationEvent
import com.dart69.todolist.splash.domain.usecase.IsAppFirstRunUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

internal class FakeIsAppFirstRunUseCase(var isAppFirstRun: Boolean) : IsAppFirstRunUseCase {
    override suspend fun invoke(): Boolean = isAppFirstRun
}

internal data class FakeDirections(
    override val actionId: Int,
) : NavDirections {
    override val arguments: Bundle = bundleOf()
}

internal class SplashViewModelTest : BaseTest.Default() {
    private val isAppFirstRunUseCase = FakeIsAppFirstRunUseCase(true)
    private val greetings = FakeDirections(1)
    private val home = FakeDirections(2)
    private lateinit var viewModel: SplashViewModel

    @Before
    override fun beforeEach() {
        viewModel = SplashViewModel(
            timeOut = 0L,
            dispatchers = TestDispatchers(),
            isAppFirstRunUseCase = isAppFirstRunUseCase,
            greetings = greetings,
            home = home,
        )
    }

    private fun testSplash(isAppFirstRun: Boolean, navDirections: NavDirections) = runBlocking {
        isAppFirstRunUseCase.isAppFirstRun = isAppFirstRun

        val expectedScreenStates = listOf(SplashScreenState.Initial, SplashScreenState.Completed)
        val expectedScreenEvents = listOf(NavigationEvent(navDirections))

        val actualScreenStates = mutableListOf<SplashScreenState>()
        val actualScreenEvents = mutableListOf<NavigationEvent>()

        val job1 = launch(UnconfinedTestDispatcher()) {
            viewModel.observeScreenState().take(expectedScreenStates.size)
                .toList(actualScreenStates)
        }
        val job2 = launch(UnconfinedTestDispatcher()) {
            viewModel.observeEvent().take(expectedScreenEvents.size).toList(actualScreenEvents)
        }

        viewModel.showSplash()
        joinAll(job1, job2)

        assertCollectionsEquals(expectedScreenStates, actualScreenStates)
        assertCollectionsEquals(expectedScreenEvents, actualScreenEvents)
    }

    @Test
    fun showSplashFirstRun() = runBlocking {
        testSplash(true, greetings)
    }

    @Test
    fun showSplashNotFirstTime() = runBlocking {
        testSplash(false, home)
    }
}