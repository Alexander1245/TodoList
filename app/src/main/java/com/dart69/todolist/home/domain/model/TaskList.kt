package com.dart69.todolist.home.domain.model

import com.dart69.todolist.R
import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model

sealed class TaskList(
    val icon: Int,
) : Model, Identifiable<String> {
    open val name: String = this::class.simpleName!!

    override fun requireIdentifier(): String = name

    object Important : TaskList(R.drawable.ic_star_rate)

    object Tasks : TaskList(R.drawable.ic_house_siding)

    /**
     * do not try create own UserDefined TaskList with id
     * */

    data class UserDefined @Deprecated(
        message = "Do not create your own UserDefined tasks using primary constructor. " +
                "Use this constructor only when you need map entities/models from data or model layers.",
        replaceWith = ReplaceWith("invoke(name)")
    ) constructor(
        override val name: String
    ) : TaskList(R.drawable.ic_user_defined_list)

    companion object {
        val PREDEFINED get() = listOf(
            Important,
            Tasks,
        )

        operator fun invoke(name: String): TaskList = UserDefined(name)
    }
}