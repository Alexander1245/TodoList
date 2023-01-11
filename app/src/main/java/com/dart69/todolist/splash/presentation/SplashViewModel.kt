package com.dart69.todolist.splash.presentation

import androidx.lifecycle.viewModelScope
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AppDispatchers
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.presentation.BaseViewModel
import com.dart69.todolist.core.presentation.ScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SplashScreenState(
    val imageRes: Int,
) : ScreenState {
    Initial(R.drawable.uno_platform_symbol),
    Completed(R.drawable.arraow_symbol),
    /*FirstRun(R.drawable.welcome_uno, true),*/
}

class SplashViewModel(
    private val timeOut: Long = 1500L,
    private val dispatchers: AvailableDispatchers = AppDispatchers,
    //private val isAppFirstRunUseCase... TODO
) : BaseViewModel<SplashScreenState>(SplashScreenState.Initial) {

    init {
        viewModelScope.launch(dispatchers.default) {
            delay(timeOut)
            screenObserver.sendScreenState(SplashScreenState.Completed)
        }
    }
}