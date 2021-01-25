package com.sevenpeakssoftware.carsdetails.utils

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun getCalendar(): Calendar {
    return Calendar.getInstance()
}

fun getSimpleDateFormat(pattern: String, locale: Locale = Locale.getDefault()): SimpleDateFormat {
    return SimpleDateFormat(pattern, locale)

}

fun formatDateTime(context: Context, date: Date = Date()): String {
    return formatDate(date) + ", " + formatTime(
        context,
        date
    )
}

private fun formatDate(date: Date = Date()): String {
    return getSimpleDateFormat(
        if (isSameYear(date.toCalendar())) {
            "dd MMMM"
        } else {
            "dd MMMM yyyy"
        }
    ).format(date)
}

private fun isSameYear(first: Calendar, second: Calendar = getCalendar()): Boolean {
    return first.get(Calendar.YEAR) == second.get(Calendar.YEAR)
}

private fun formatTime(context: Context, date: Date = Date()): String {
    return DateFormat.getTimeFormat(context).format(date)
}

fun Date.toCalendar(): Calendar {
    return getCalendar().also {
        it.timeInMillis = time
    }
}