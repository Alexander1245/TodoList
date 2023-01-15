package com.dart69.todolist.home.domain.model

import com.dart69.todolist.R
import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model

sealed class TaskList(
    val icon: Int,
): Model, Identifiable<String> {
    open val name: String = this::class.simpleName!!

    override fun requireIdentifier(): String = name

    object Important : TaskList(R.drawable.ic_star_rate)

    object Tasks : TaskList(R.drawable.ic_house_siding)

    data class UserDefined(override val name: String) : TaskList(R.drawable.ic_user_defined_list)

    companion object {
        val PREDEFINED = listOf(
            Important,
            Tasks,
        )

        operator fun invoke(name: String): TaskList = UserDefined(name)
    }
}