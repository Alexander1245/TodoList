package com.dart69.todolist.greetings.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavDestination
import com.dart69.todolist.R
import com.dart69.todolist.core.presentation.CommunicatorViewModel
import com.dart69.todolist.core.presentation.ScreenEvent
import com.dart69.todolist.core.presentation.ScreenState

data class GreetingsScreenState(
    @DrawableRes val imageRes: Int,
    @StringRes val greetings: Int,
    @StringRes val hint: Int,
) : ScreenState {

    companion object {
        val DEFAULT = GreetingsScreenState(R.drawable.welcome_uno, R.string.greetings, R.string.hint)
    }
}

data class GetStartedEvent(
    val destination: NavDestination
) : ScreenEvent

class GreetingsViewModel(

) : CommunicatorViewModel<GreetingsScreenState, GetStartedEvent>(GreetingsScreenState.DEFAULT) {

}