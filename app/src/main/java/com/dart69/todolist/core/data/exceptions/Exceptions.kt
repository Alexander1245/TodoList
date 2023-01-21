package com.dart69.todolist.core.data.exceptions

import android.database.sqlite.SQLiteException

abstract class RoomException(
    message: String = "Room exception occurred",
) : SQLiteException(message)

class UniqueConstraintException(
    message: String = "Unique constraint failed"
) : RoomException(message)

fun interface ErrorMapper<I : Throwable, O : RoomException> {
    fun map(throwable: I): O
}

fun Throwable.toRoomException(): RoomException = object : RoomException(message.orEmpty()) {

}