package com.fahima.assessment.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        Locale.getDefault()
    )
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    return try {
        dateFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String? {
    return try {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        formatter.format(this)
    } catch (e: java.lang.Exception) {
        null
    }
}


