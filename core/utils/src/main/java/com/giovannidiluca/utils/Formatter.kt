package com.giovannidiluca.utils

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatterUtils {
    private const val MONTH_DAY_YEAR_FORMAT = "MMM, dd yyyy"
    private const val STANDARD_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    private fun formatter(pattern: String) = DateTimeFormatter.ofPattern(pattern)

    fun transformDate(date: String): String {
        return try {
            LocalDateTime.parse(date, formatter(STANDARD_DATE_TIME_FORMAT))
                .atZone(ZoneOffset.UTC)
                .format(formatter(MONTH_DAY_YEAR_FORMAT))
        } catch (e: DateTimeParseException) {
            date
        }
    }
}