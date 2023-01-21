package com.dart69.todolist.home.domain.model

import com.dart69.todolist.R
import com.dart69.todolist.core.domain.Identifiable
import com.dart69.todolist.core.domain.Model

sealed class TaskList(
    val icon: Int,
) : Model, Identifiable<Long> {
    abstract val id: Long

    open val name: String = this::class.simpleName!!

    override fun requireIdentifier(): Long = id

    object Important : TaskList(R.drawable.ic_star_rate) {
        override val id: Long = 0L
    }

    object Tasks : TaskList(R.drawable.ic_house_siding) {
        override val id: Long = 1L
    }

    data class UserDefined(
        override val id: Long,
        override val name: String
    ) : TaskList(R.drawable.ic_user_defined_list)

    companion object {
        val PREDEFINED = listOf(
            Important,
            Tasks,
        )

        operator fun invoke(name: String): TaskList = UserDefined(0L, name)
    }
}