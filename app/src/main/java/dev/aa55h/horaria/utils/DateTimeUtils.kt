package dev.aa55h.horaria.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDate.formatted(): String? {
    return format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
}

fun localDate(millis: Long): LocalDate {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}