package com.andydotdaniel.jajanku.utils

import java.text.NumberFormat

actual class NumberFormatter {
    private val decimalFormat = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 0

        isGroupingUsed = true
    }
    actual fun format(value: Double): String = decimalFormat.format(value)
}

