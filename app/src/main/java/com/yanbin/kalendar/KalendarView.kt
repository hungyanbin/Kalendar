package com.yanbin.kalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.yanbin.kalendar.ui.theme.KalendarTheme
import java.time.LocalDate
import java.time.Month

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

@Composable
fun DefaultKalendarView(
    modifier: Modifier = Modifier,
    kalendarRange: KalendarRange
) {
    var selectedDate by remember {
        mutableStateOf(kalendarRange.beginDate)
    }

    KalendarView(
        modifier = modifier,
        kalendarRange = kalendarRange,
        dayView = { localDate, month, dayViewModifier ->
            val selected = localDate == selectedDate
            DefaultDayView(
                modifier = dayViewModifier,
                date = localDate,
                month = month,
                selected = selected,
                onClicked = {
                    selectedDate = it
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
                textAlign = TextAlign.Center
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