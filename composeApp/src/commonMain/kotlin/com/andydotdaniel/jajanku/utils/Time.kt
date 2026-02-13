package com.andydotdaniel.jajanku.utils

import kotlin.time.Duration.Companion.milliseconds


object Time {

    fun formatTimestamp(timestamp: Long): String {
        val duration = timestamp.milliseconds

        return duration.toComponents { hour, minutes ->
            val paddedHour = hour.toString().padStart(2, '0')
            val paddedMinutes = minutes.toString().padStart(2, '0')
            "${paddedHour}:${paddedMinutes}"
        }
    }

}

