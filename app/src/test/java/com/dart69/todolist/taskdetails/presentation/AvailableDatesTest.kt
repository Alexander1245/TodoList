package com.dart69.todolist.taskdetails.presentation

import com.BaseTest
import com.dart69.todolist.task.domain.AvailableDates
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

internal class AvailableDatesTest : BaseTest {
    @Test
    fun testFactory() {
        val factory = AvailableDates.Factory
        val today = LocalDate.now()
        val custom = today.plusMonths(1).toEpochDay()
        assertEquals(AvailableDates.Today, factory.create(today.toEpochDay()))
        assertEquals(AvailableDates.Tomorrow, factory.create(today.plusDays(1).toEpochDay()))
        assertEquals(AvailableDates.NextWeek, factory.create(today.plusWeeks(1).toEpochDay()))
        assertEquals(AvailableDates.Custom(custom), factory.create(custom))
    }
}