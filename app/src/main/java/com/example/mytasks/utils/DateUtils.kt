package com.example.mytasks.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatarDataCurta(ms: Long): String =
    SimpleDateFormat("dd MMM", Locale("pt", "BR")).format(Date(ms))

fun formatarData(ms: Long): String =
    SimpleDateFormat("dd MMM yyyy", Locale("pt", "BR")).format(Date(ms))

fun startOfTodayUtcMs(): Long =
    Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

fun isHoje(ms: Long): Boolean {
    val todayUtc = startOfTodayUtcMs()
    return ms >= todayUtc && ms < todayUtc + 86_400_000L
}

fun isPassado(ms: Long): Boolean = ms < startOfTodayUtcMs()
