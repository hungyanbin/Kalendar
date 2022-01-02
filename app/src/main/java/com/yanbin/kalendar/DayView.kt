package com.yanbin.kalendar

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yanbin.kalendar.ui.theme.KalendarTheme
import java.time.LocalDate
import java.time.Month

@Composable
fun DayView(
    modifier: Modifier,
    date: LocalDate,
    month: Month? = null,
    selected: Boolean = false,
    onClicked: (LocalDate) -> Unit = {},
    selectedView: @Composable (Modifier, Boolean) -> Unit =
        { modifier, selected -> CircleSelectedContainer(modifier, selected)}
) {
    Log.i("testt", "DayView: ${date.dayOfMonth}")

    Box(modifier = modifier.clickable { onClicked(date) }) {
        selectedView(
            Modifier
                .size(40.dp)
                .align(Alignment.Center), selected)
        if (date.month == month) {
            Text(
                text = date.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 4.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
fun CircleSelectedContainer(
    modifier: Modifier,
    selected: Boolean
) {
    val selectedColor = if (selected) Color.Cyan else MaterialTheme.colors.onPrimary
    val colorState = animateColorAsState(targetValue = selectedColor)

    Surface(
        modifier = modifier,
        color = colorState.value,
        shape = CircleShape
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun DayViewPreview() {
    KalendarTheme {
        DayView(
            modifier = Modifier.size(50.dp),
            date = LocalDate.of(2022, 1, 1),
            month = Month.JANUARY
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedDayViewPreview() {
    KalendarTheme {
        DayView(
            modifier = Modifier.size(50.dp),
            date = LocalDate.of(2022, 1, 1),
            month = Month.JANUARY,
            selected = true,
        )
    }
}