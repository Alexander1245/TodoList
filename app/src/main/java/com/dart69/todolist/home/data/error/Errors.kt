package com.dart69.todolist.home.data.error

abstract class NameException(message: String = "") : IllegalArgumentException(message)

data class EmptyNameException(
    override val message: String = "Name can't be empty"
) : NameException()

data class NameTooLongException(
    override val message: String = "Name length too long"
) : NameException()

data class NotUniqueNameException(
    override val message: String = "Name already exists"
) : NameException()
