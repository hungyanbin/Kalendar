package com.yanbin.kalendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yanbin.kalendar.ui.theme.KalendarTheme
import java.time.LocalDate
import java.time.Month

/**
 * Stateless Kalendar View
 */
@Composable
fun KalendarView(
    modifier: Modifier = Modifier,
    kalendarRange: KalendarRange,
    dayContent: @Composable RowScope.(LocalDate, Month?) -> Unit
) {
    val columnCount = 7

    Column(modifier = modifier) {
        for (row in 0 until kalendarRange.numberOfRow) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (column in 0 until columnCount) {
                    val date =
                        kalendarRange.beginDate.plusDays((column + columnCount * row).toLong())

                    dayContent(date, kalendarRange.currentMonth)
                }
            }
        }
    }
}

@Composable
fun SelectableKalendarView(
    modifier: Modifier = Modifier,
    kalendarRange: KalendarRange,
    dateSelection: DateSelection
) {
    KalendarView(
        modifier = modifier,
        kalendarRange = kalendarRange,
        // provide day data
        dayContent = { localDate, month ->
            val selected by dateSelection.isSelected(localDate).collectAsState(initial = false)

            key(localDate.toString(), selected) {
                DayView(
                    modifier = Modifier.weight(1f),
                    date = localDate,
                    month = month,
                    selected = selected,
                    onClicked = { dateSelection.select(localDate) }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun KalendarViewPreview() {
    KalendarTheme {
        val kalendarScope = rememberCoroutineScope()
        val dateSelection: DateSelection = SingleDateSelection(coroutineScope = kalendarScope)
        SelectableKalendarView(
            modifier = Modifier.fillMaxHeight(),
            kalendarRange = KalendarRange.ofMonth(2022, Month.FEBRUARY),
            dateSelection = dateSelection
        )
    }
}
