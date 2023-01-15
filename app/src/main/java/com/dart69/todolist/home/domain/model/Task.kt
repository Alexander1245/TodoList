package com.dart69.todolist.home.domain.model

import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model

data class Task(
    val id: Long,
) : Identifiable<Long>, Model {
    override fun requireIdentifier(): Long = id
}
