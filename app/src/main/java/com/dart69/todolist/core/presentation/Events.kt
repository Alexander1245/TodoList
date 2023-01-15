package com.dart69.todolist.core.presentation

import androidx.navigation.NavDirections

data class NavigationEvent(val directions: NavDirections) : ScreenEvent

object CloseScreenEvent : ScreenEvent