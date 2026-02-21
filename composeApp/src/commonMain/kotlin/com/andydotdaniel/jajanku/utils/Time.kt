package com.andydotdaniel.jajanku.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant


object Time {

    fun formatTimestamp(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val paddedHour = localDateTime.hour.toString().padStart(2, '0')
        val paddedMinutes = localDateTime.minute.toString().padStart(2, '0')
        return "${paddedHour}:${paddedMinutes}"
    }

}

