package com.dart69.todolist.splash.presentation

import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.BaseViewModel
import com.dart69.todolist.core.presentation.ScreenState

enum class SplashScreenState(
    val imageRes: Int,
    val isGreetingsVisible: Boolean
) : ScreenState {
    Initial(R.drawable.uno_platform_symbol, false),
    Completed(R.drawable.arraow_symbol, false),
    /*FirstRun(R.drawable.welcome_uno, true),*/
}

class SplashViewModel : BaseViewModel<SplashScreenState>(SplashScreenState.Initial) {
}