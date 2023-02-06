package com.sevenpeakssoftware.kyawlinnthant.core

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

object DateTimeConverter {

    fun transform(is24: Boolean, input: String): String {

        val currentYear = LocalDateTime.now().year
        val pattern = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm",
            Locale.getDefault()
        )
        val sameYearFormat = if (is24)
            DateTimeFormatter.ofPattern("dd MMM,HH:mm", Locale.getDefault())
        else
            DateTimeFormatter.ofPattern("dd MMM,hh:mm a", Locale.getDefault())
        val notSameYearFormat = if (is24)
            DateTimeFormatter.ofPattern("dd MMM yyyy,HH:mm", Locale.getDefault())
        else
            DateTimeFormatter.ofPattern("dd MMM yyyy,hh:mm a", Locale.getDefault())
        val inputDate: LocalDateTime

        return try {

            inputDate = LocalDateTime.parse(input, pattern)
            val inputYear = inputDate.year
            val withinCurrentYear = currentYear == inputYear
            if (withinCurrentYear) inputDate.format(sameYearFormat) else inputDate.format(
                notSameYearFormat
            )
        } catch (e: DateTimeParseException) {
            ""
        }
    }
}