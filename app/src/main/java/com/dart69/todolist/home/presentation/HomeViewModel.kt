package com.dart69.todolist.home.presentation

import com.dart69.todolist.core.presentation.BaseViewModel
import com.dart69.todolist.core.presentation.ScreenState

sealed class HomeScreenState : ScreenState {
    object Initial : HomeScreenState()
}

class HomeViewModel : BaseViewModel<HomeScreenState>(HomeScreenState.Initial) {
}