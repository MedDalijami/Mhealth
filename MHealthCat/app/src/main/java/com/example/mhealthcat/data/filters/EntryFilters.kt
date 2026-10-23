package com.example.mhealthcat.data.filters

import com.example.mhealthcat.data.repositories.Entry
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

enum class TimeFilter(val displayName: String, val days: Long?) {
    ALL("Vsi vnosi", null),
    TODAY("Danes", 1),
    LAST_7_DAYS("Zadnjih 7 dni", 7),
    LAST_30_DAYS("Zadnjih 30 dni", 30),
    CUSTOM("Izberi obdobje …", null)
}

object EntryFilter {
    val ALL_RATINGS = 1..5

    // Last x days = today + (x-1) days
    fun period(
        time: TimeFilter,
        customPeriod: ClosedRange<LocalDate>?,
        today: LocalDate = LocalDate.now()
    ): ClosedRange<LocalDate>? = when (time) {
        TimeFilter.ALL -> null
        TimeFilter.TODAY -> today..today
        TimeFilter.LAST_7_DAYS, TimeFilter.LAST_30_DAYS -> today.minusDays(time.days!! - 1)..today
        TimeFilter.CUSTOM -> customPeriod
    }

    fun <T : Entry> apply(
        items: List<T>,
        period: ClosedRange<LocalDate>?,
        ratingRange: IntRange,
        zone: ZoneId = ZoneId.systemDefault()
    ): List<T> = items.filter { item ->
        val day = Instant.ofEpochMilli(item.timestamp).atZone(zone).toLocalDate()
        val insidePeriod = period == null || day in period
        insidePeriod && item.rating in ratingRange
    }
}