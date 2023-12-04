package com.theo.composecalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.theo.composecalendar.models.HighlightDate
import java.time.Month

/**
 * A Month Long Calendar View
 */
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    forMonth: Month = getCurrentMonth(),
    forYear: Int = getCurrentYear(),
    highlightDate: HighlightDate = HighlightDate.None,
) {
    val days = getCompleteDateRangeForMonth(
        month = forMonth,
        year = forYear,
    )

    val numWeeks = getNumWeeksForCalendarView(
        month = forMonth,
        year = forYear,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White), // TODO
    ) {
        (0 until numWeeks).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                (0 until 7).forEach { day ->
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val date = days[7 * week + day]
                        Text(
                            text = "${date.dayOfMonth}",
                            color = Color.Black,
                            modifier = Modifier.then(
                                when (highlightDate) {
                                    is HighlightDate.None -> {
                                        Modifier
                                    }

                                    is HighlightDate.Current -> {
                                        val current = getCurrentLocalDate()
                                        if (current == date) {
                                            Modifier.background(color = Color.Red)
                                        } else {
                                            Modifier
                                        }
                                    }
                                }

                            )
                        )
                    }
                }
            }
        }
    }
}
