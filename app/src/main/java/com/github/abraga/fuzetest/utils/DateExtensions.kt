package com.github.abraga.fuzetest.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

private const val API_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

enum class Pattern(private val type: String) {
    PATTERN_TIME("HH:mm"),
    PATTERN_WEEK("EEE, HH:mm"),
    PATTERN_DATE("dd.MM HH:mm");

    override fun toString(): String { return type }
}

fun String.format(pattern: Pattern): String {
    return try {
        val date = timeStringToDate() ?: return this
        val dateFormat = SimpleDateFormat(pattern.toString(), Locale.getDefault())
        dateFormat.format(date)
    } catch (e: Exception) {
        this
    }
}

fun String.isToday(): Boolean {
    val date = timeStringToDate() ?: return false
    return DateUtils.isToday(date.time)
}

fun String.isThisWeek(): Boolean {
    val date = timeStringToDate() ?: return false

    val currentCalendar = Calendar.getInstance()
    val week = currentCalendar.get(Calendar.WEEK_OF_YEAR)
    val year = currentCalendar.get(Calendar.YEAR)

    val targetCalendar = Calendar.getInstance()
    targetCalendar.time = date
    val targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR)
    val targetYear = targetCalendar.get(Calendar.YEAR)

    return week == targetWeek && year == targetYear
}

private fun String.timeStringToDate(): Date? =
    SimpleDateFormat(
        API_DATETIME_PATTERN,
        Locale.getDefault()
    ).run {
        parse(this@timeStringToDate)
    }