package com.yanbin.kalendar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

interface DateSelection {
    fun isSelected(date: LocalDate): Flow<Boolean>
    fun select(date: LocalDate)
}

internal class SingleDateSelection(
    private val selectedDate: LocalDate? = null,
    private val coroutineScope: CoroutineScope
): DateSelection{

    private val flow = MutableStateFlow(selectedDate)

    override fun isSelected(date: LocalDate) = flow.map { date == it }

    override fun select(date: LocalDate) {
        coroutineScope.launch {
            flow.emit(date)
        }
    }
}

internal class MultipleDateSelection(
    private val selectedDates: MutableSet<LocalDate> = mutableSetOf(),
    private val coroutineScope: CoroutineScope
): DateSelection {

    private val flow = MutableStateFlow(selectedDates.toMutableSet())

    override fun isSelected(date: LocalDate): Flow<Boolean> {
        return flow.map { set -> set.contains(date) }
    }

    override fun select(date: LocalDate) {
        coroutineScope.launch {
            if (selectedDates.contains(date)) {
                selectedDates.remove(date)
            } else {
                selectedDates.add(date)
            }
            flow.emit(selectedDates.toMutableSet())
        }
    }
}