package com.dart69.todolist.greetings.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.dart69.todolist.R
import com.dart69.todolist.core.coroutines.AvailableDispatchers
import com.dart69.todolist.core.di.ApplicationScope
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.NavigationEvent
import com.dart69.todolist.core.presentation.ScreenState
import com.dart69.todolist.greetings.di.HomeDirectionFromGreetings
import com.dart69.todolist.splash.domain.usecase.RunAppFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GreetingsScreenState(
    @DrawableRes val imageRes: Int,
    @StringRes val greetings: Int,
    @StringRes val hint: Int,
) : ScreenState {

    companion object {
        val DEFAULT =
            GreetingsScreenState(R.drawable.welcome_uno, R.string.greetings, R.string.hint)
    }
}

@HiltViewModel
class GreetingsViewModel @Inject constructor(
    private val dispatchers: AvailableDispatchers,
    @HomeDirectionFromGreetings private val home: NavDirections,
    private val runAppFirstTimeUseCase: RunAppFirstTimeUseCase,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : CommunicatorViewModel<GreetingsScreenState, NavigationEvent>(GreetingsScreenState.DEFAULT) {

    fun getStarted() {
        applicationScope.launch(dispatchers.default) {
            runAppFirstTimeUseCase()
        }
        viewModelScope.launch(dispatchers.default) {
            eventObserver.sendEvent(NavigationEvent(home))
        }
    }
}