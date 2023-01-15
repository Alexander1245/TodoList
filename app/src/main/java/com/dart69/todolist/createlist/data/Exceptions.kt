package com.dart69.todolist.createlist.data

import androidx.annotation.StringRes
import com.dart69.todolist.R
import com.dart69.todolist.core.data.exceptions.RoomException

data class UniqueIdException(
    @StringRes override val messageResource: Int = R.string.item_already_exists,
): RoomException()

