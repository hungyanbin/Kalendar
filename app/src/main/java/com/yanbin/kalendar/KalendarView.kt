package com.yanbin.kalendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yanbin.kalendar.ui.theme.KalendarTheme
import java.time.LocalDate

@Composable
fun KalendarView(
    modifier: Modifier = Modifier,
    kalendarRange: KalendarRange,
    dayView: @Composable (LocalDate, Modifier) -> Unit
) {
    val columnCount = 7
    Column(modifier = modifier) {
        for (row in 0 until kalendarRange.numberOfRow) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for(column in 0..columnCount) {
                    val date = kalendarRange.beginDate.plusDays((column + columnCount * row).toLong())
                    dayView(date, Modifier.weight(1f))
                }
            }
        }
    }
}

class KalendarRange(
    val beginDate: LocalDate,
    val numberOfRow: Int
)

@Preview(showBackground = true)
@Composable
fun KalendarViewPreview(){
    KalendarTheme {
        KalendarView(
            modifier = Modifier.height(100.dp),
            kalendarRange = KalendarRange(LocalDate.now(), 4),
            dayView = { localDate, modifier ->
                Text(
                    text = localDate.dayOfMonth.toString(),
                    modifier = modifier,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}