package com.yanbin.kalendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

data class KalendarRange(
    val beginDate: LocalDate,
    val numberOfRow: Int,
    val currentMonth: Month?
) {
    companion object {
        fun ofMonth(year: Int, month: Month): KalendarRange {
            val firstDayOfWeek = DayOfWeek.SUNDAY.value
            val firstDayOfMonth = LocalDate.of(year, month, 1)
            val weekDayOfFirstDay = firstDayOfMonth.dayOfWeek.value
            val firstDayOffset = (weekDayOfFirstDay - firstDayOfWeek + 7) % 7
            val beginDate = firstDayOfMonth.minusDays(firstDayOffset.toLong())
            val totalDays = ChronoUnit.DAYS.between(beginDate, firstDayOfMonth.plusMonths(1)) - 1

            return KalendarRange(
                beginDate,
                ceil(totalDays.toFloat()/7).toInt(),
                month
            )
        }
    }
}
