package com.dart69.todolist.home.presentation

import com.dart69.todolist.core.presentation.BaseViewModel
import com.dart69.todolist.core.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class HomeScreenState : ScreenState {
    object Initial : HomeScreenState()
}

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeScreenState>(HomeScreenState.Initial) {
}