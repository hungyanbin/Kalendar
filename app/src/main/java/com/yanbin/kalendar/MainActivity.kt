package com.yanbin.kalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yanbin.kalendar.ui.theme.KalendarTheme
import java.time.Month

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalendarTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val kalendarScope = rememberCoroutineScope()
                    val dateSelection: DateSelection = MultipleDateSelection(coroutineScope = kalendarScope)

                    SelectableKalendarView(
                        modifier = Modifier.fillMaxHeight(),
                        kalendarRange = KalendarRange.ofMonth(2022, Month.JANUARY),
                        dateSelection = dateSelection
                    )
                }
            }
        }
    }
}
