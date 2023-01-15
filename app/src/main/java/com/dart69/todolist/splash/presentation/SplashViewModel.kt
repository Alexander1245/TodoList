package com.dart69.todolist.splash.presentation

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.NavigationEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.splash.di.GreetingsDirection
import com.dart69.todolist.splash.di.HomeDirection
import com.dart69.todolist.splash.di.TimeOut
import com.dart69.todolist.splash.domain.usecase.IsAppFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

enum class SplashScreenState(
    val imageRes: Int,
) : ScreenState {
    Initial(R.drawable.uno_platform_symbol),
    Completed(R.drawable.arraow_symbol),
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    @TimeOut private val timeOut: Long,
    private val dispatchers: AvailableDispatchers,
    private val isAppFirstRunUseCase: IsAppFirstRunUseCase,
    @GreetingsDirection private val greetings: NavDirections,
    @HomeDirection private val home: NavDirections,
) : CommunicatorViewModel<SplashScreenState, NavigationEvent>(SplashScreenState.Initial) {
    private val isSplashShown = AtomicBoolean(false)

    fun showSplash() {
        if (isSplashShown.compareAndSet(false, true)) {
            viewModelScope.launch(dispatchers.default) {
                delay(timeOut)
                screenObserver.sendScreenState(SplashScreenState.Completed)
                delay(timeOut)
                val direction = if (isAppFirstRunUseCase()) greetings else home
                eventObserver.sendEvent(NavigationEvent(direction))
            }
        }
    }
}