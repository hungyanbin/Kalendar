package com.yanbin.kalendar

import java.time.LocalDate

interface DateSelection {
    fun isSelected(date: LocalDate): Boolean
    fun select(date: LocalDate): DateSelection
}

internal class SingleDateSelection(
    private val selectedDate: LocalDate? = null
): DateSelection{
    override fun isSelected(date: LocalDate) = date == selectedDate

    override fun select(date: LocalDate): DateSelection {
        val selectedDate = if (date == selectedDate) {
            null
        } else {
            date
        }
        return SingleDateSelection(selectedDate)
    }
}

internal class MultipleDateSelection(
    private val selectedDates: Set<LocalDate> = setOf()
): DateSelection {
    override fun isSelected(date: LocalDate): Boolean {
        return selectedDates.contains(date)
    }

    override fun select(date: LocalDate): DateSelection {
        val newSelectedDates = selectedDates.toMutableSet()
        if (selectedDates.contains(date)) {
            newSelectedDates.remove(date)
        } else {
            newSelectedDates.add(date)
        }
        return MultipleDateSelection(newSelectedDates)
    }
}