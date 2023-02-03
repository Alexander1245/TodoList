package com.dart69.todolist.task.domain

import android.os.Parcelable
import com.dart69.todolist.R
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
sealed class AvailableDates : Parcelable {
    abstract val date: LocalDate

    interface LabelOwner {
        val label: Int
    }

    fun toEpochDate(): Long = date.toEpochDay()

    object Today : AvailableDates(), LabelOwner {
        override val date: LocalDate get() = LocalDate.now()

        override val label: Int get() = R.string.today
    }

    object Tomorrow : AvailableDates(), LabelOwner {
        override val date: LocalDate get() = Today.date.plusDays(1)

        override val label: Int get() = R.string.tomorrow
    }

    object NextWeek : AvailableDates(), LabelOwner {
        override val date: LocalDate get() = Today.date.plusWeeks(1)

        override val label: Int get() = R.string.next_week
    }

    data class Custom(val time: Long) : AvailableDates() {
        override val date: LocalDate get() = LocalDate.ofEpochDay(time)
    }

    interface Factory {
        fun create(epoch: Long): AvailableDates

        companion object : Factory {
            override fun create(epoch: Long): AvailableDates =
                when (LocalDate.ofEpochDay(epoch)) {
                    Today.date -> Today
                    Tomorrow.date -> Tomorrow
                    NextWeek.date -> NextWeek
                    else -> Custom(epoch)
                }
        }
    }
}

class UndefinedDateException : Exception()