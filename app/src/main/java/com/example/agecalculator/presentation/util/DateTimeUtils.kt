package com.example.agecalculator.presentation.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun Long?.toFormattedDateString(): String {
    val instant = Instant.fromEpochMilliseconds(this ?: System.currentTimeMillis())
    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val formatter = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
        char(',')
        char(' ')
        year()
    }
    return formatter.format(date)
}