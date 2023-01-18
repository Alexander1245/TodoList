package com.dart69.todolist.home.domain

import com.dart69.todolist.core.coroutines.ResultsFlow

interface NameParser {
    fun matches(name: String): ResultsFlow<Boolean>
}