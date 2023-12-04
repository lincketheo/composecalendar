package com.theo.composecalendar

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import kotlin.math.ceil


internal fun currentInstant(): Instant {
    return Clock.System.now()
}

internal fun getCurrentLocalDate(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate {
    return currentInstant().toLocalDateTime(timeZone).date
}

internal fun getCurrentMonth(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Month {
    return currentInstant()
        .toLocalDateTime(timeZone)
        .month
}

internal fun getCurrentYear(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Int {
    return currentInstant()
        .toLocalDateTime(timeZone)
        .year
}

internal fun getFirstDayOfLandingMonthYear(
    month: Month = getCurrentMonth(),
    year: Int = getCurrentYear()
): LocalDate {
    return LocalDate(year, month, 1)
}

internal fun getMondayBeforeOrOnFirstDayOfMonth(
    month: Month = getCurrentMonth(),
    year: Int = getCurrentYear(),
): LocalDate {
    val firstDay = getFirstDayOfLandingMonthYear(month, year)
    val subtractDays = 7 - getFirstDayOfLandingMonthYear(month, year).dayOfWeek.value
    return firstDay.minus(DatePeriod(days = subtractDays))
}

internal fun getNumberOfDays(month: Month, year: Int): Int {
    val firstDay = getFirstDayOfLandingMonthYear(month, year)
    val yearUpdated = if (month == Month.DECEMBER) {
        year + 1
    } else {
        year
    }

    val firstDayOfNextMonth = getFirstDayOfLandingMonthYear(month.plus(1), yearUpdated)

    return firstDay.until(firstDayOfNextMonth, DateTimeUnit.DAY)
}

internal fun getNumWeeksForCalendarView(
    month: Month,
    year: Int,
): Int {
    val numDaysOfTheMonth = getNumberOfDays(month, year)
    return ceil(numDaysOfTheMonth / 7.0).toInt()
}

internal fun getCompleteDateRangeForMonth(
    month: Month = getCurrentMonth(),
    year: Int = getCurrentYear(),
): List<LocalDate> {
    val firstDay = getMondayBeforeOrOnFirstDayOfMonth(month, year)
    val numDays = getNumWeeksForCalendarView(month, year) * 7
    return (0 until numDays).map { firstDay.plus(DatePeriod(days = it)) }
}

