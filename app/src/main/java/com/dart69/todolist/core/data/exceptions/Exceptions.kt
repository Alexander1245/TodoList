package com.dart69.todolist.core.data.exceptions

import com.dart69.todolist.R

interface BaseException {
    val messageResource: Int
}

abstract class RoomException(
    message: String = "Room exception occurred",
) : IllegalArgumentException(), BaseException

fun interface ErrorMapper<I: Throwable, O: BaseException> {
    fun map(throwable: I): O
}

fun Throwable.toRoomException(): RoomException = object : RoomException(message.orEmpty()) {
    override val messageResource: Int = R.string.internal_exception
}