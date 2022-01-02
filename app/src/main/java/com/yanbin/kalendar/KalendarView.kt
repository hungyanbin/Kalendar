package com.yanbin.kalendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    dayView: @Composable (LocalDate, Month?, Modifier) -> Unit
) {
    val columnCount = 7
    Column(modifier = modifier) {
        for (row in 0 until kalendarRange.numberOfRow) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for(column in 0 until columnCount) {
                    val date = kalendarRange.beginDate.plusDays((column + columnCount * row).toLong())
                    dayView(date, kalendarRange.currentMonth, Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Stateful Kalendar View
 */
@Composable
fun DefaultKalendarView(
    modifier: Modifier = Modifier,
    kalendarRange: KalendarRange,
    selectionMode: SelectionMode = SelectionMode.Single
) {
    val dateSelection: DateSelection = when(selectionMode) {
        SelectionMode.Single -> SingleDateSelection()
        SelectionMode.Multiple -> MultipleDateSelection()
    }

    var selectedDate by remember {
        mutableStateOf(dateSelection)
    }

    KalendarView(
        modifier = modifier,
        kalendarRange = kalendarRange,
        dayView = { localDate, month, dayViewModifier ->
            val selected = selectedDate.isSelected(localDate)
            DefaultDayView(
                modifier = dayViewModifier,
                date = localDate,
                month = month,
                selected = selected,
                onClicked = {
                    selectedDate = selectedDate.select(it)
                }
            )
        }
    )
}

@Composable
fun DefaultDayView(
    modifier: Modifier,
    date: LocalDate,
    month: Month? = null,
    selected: Boolean = false,
    onClicked: (LocalDate) -> Unit = {}
) {
    val backgroundColor = if(selected) Color.Cyan else Color.Transparent
    Surface(
        modifier = modifier.clickable { onClicked(date) },
        color = backgroundColor
    ) {
        if (date.month == month) {
            Text(
                text = date.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KalendarViewPreview(){
    KalendarTheme {
        DefaultKalendarView(
            modifier = Modifier.fillMaxHeight(),
            kalendarRange = KalendarRange.ofMonth(2022, Month.FEBRUARY),
        )
    }
}