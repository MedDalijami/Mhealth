package com.example.mhealthcat.data.points

import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.elementsAndClasses.DataType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

// How the cat feels, based on today's points
enum class Mood(val displayName: String, val emoji: String, val minPoints: Int) {
    NEUTRAL("Mucka se počuti okej", "😐", 0),
    CONTENT("Mucka je zadovoljna z današnjim dnem", "🙂", 10),
    HAPPY("Mucka je vesela", "😊", 30),
    VERY_HAPPY("Mucka se počuti super", "😻", 50)
}

// One logged entry, reduced to what matters for points
data class ScoredEntry(
    val category: DataType,
    val timestamp: Long,
    val points: Int
)


object PointsCalculator {
    const val ENTRY_POINTS = 10
    const val GOOD_SLEEP_POINTS = 20
    const val GOOD_SLEEP_MINUTES = 8 * 60

    const val MAX_DAILY_POINTS = GOOD_SLEEP_POINTS + 3 * ENTRY_POINTS


    fun sleepPoints(sleep: SleepForm): Int =
        if (sleep.hours * 60 + sleep.minutes >= GOOD_SLEEP_MINUTES) GOOD_SLEEP_POINTS else ENTRY_POINTS

    fun pointsPerDay(entries: List<ScoredEntry>, zone: ZoneId = ZoneId.systemDefault()): Map<LocalDate, Int> =
        entries
            .groupBy { Instant.ofEpochMilli(it.timestamp).atZone(zone).toLocalDate() }
            .mapValues { (_, entriesOfDay) ->
                entriesOfDay
                    .groupBy { it.category }
                    .values
                    .sumOf { entriesOfCategory -> entriesOfCategory.maxOf { it.points } }
            }

    fun todayPoints(
        entries: List<ScoredEntry>,
        today: LocalDate = LocalDate.now(),
        zone: ZoneId = ZoneId.systemDefault()
    ): Int = pointsPerDay(entries, zone)[today] ?: 0

    fun totalPoints(entries: List<ScoredEntry>, zone: ZoneId = ZoneId.systemDefault()): Int =
        pointsPerDay(entries, zone).values.sum()
    fun mood(points: Int): Mood =
        Mood.entries.last { points >= it.minPoints }
}