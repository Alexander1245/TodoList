package com.dart69.todolist.task.data

class NonExistingTaskError(id: Long): IllegalArgumentException(MESSAGE + id) {

    private companion object {
        const val MESSAGE = "Can't find task with id: "
    }
}