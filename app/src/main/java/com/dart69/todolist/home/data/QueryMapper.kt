package com.dart69.todolist.home.data

import javax.inject.Inject

fun interface QueryMapper {
    fun map(query: String): String
}

class RoomQueryMapper @Inject constructor() : QueryMapper {
    override fun map(query: String): String = "%$query%"
}