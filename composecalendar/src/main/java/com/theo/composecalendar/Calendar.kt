package com.theo.composecalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.toJavaLocalDate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

/**
 * A Month Long Calendar View
 */
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    forMonth: Month = getCurrentMonth(),
    forYear: Int = getCurrentYear(),
    labelForDayOfWeek: (@Composable (DayOfWeek) -> Unit)? = null,
    dateContent: @Composable (LocalDate) -> Unit,
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
    ) {
        if (labelForDayOfWeek != null) {
            Row(
                modifier = Modifier.weight(0.2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                (1..7).forEach { day ->
                    Column(
                        modifier = Modifier
                            .leftBorder(
                                strokeWidth = 1.dp,
                                color = Color.Black,
                                heightPercent = 0.3f
                            )
                            .bottomBorder(strokeWidth = 1.dp, color = Color.Black)
                            .weight(1f)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        labelForDayOfWeek(DayOfWeek.of(day))
                    }
                }
            }
        }

        (0 until numWeeks).forEach { week ->
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                (0 until 7).forEach { day ->
                    Column(
                        modifier = Modifier
                            .bottomBorder(strokeWidth = 1.dp, color = Color.Black)
                            .leftBorder(strokeWidth = 1.dp, color = Color.Black)
                            .weight(1f)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        dateContent(days[week * 7 + day].toJavaLocalDate())
                    }
                }
            }
        }
    }
}

@Composable
fun LabeledDateCalendar(
    modifier: Modifier = Modifier,
    forMonth: Month = getCurrentMonth(),
    forYear: Int = getCurrentYear(),
    today: LocalDate? = getCurrentLocalDate().toJavaLocalDate(),
) {
    Calendar(
        modifier = modifier,
        forMonth = forMonth,
        forYear = forYear,
        labelForDayOfWeek = {
            Text(text = it.name[0].toString())
        }
    ) {
        Column(
            //modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                modifier = Modifier.then(
                    if (it == today) {
                        Modifier.background(color = Color.White)
                    } else {
                        Modifier
                    }
                ),
                text = it.dayOfMonth.toString(),
            )
        }
    }
}
