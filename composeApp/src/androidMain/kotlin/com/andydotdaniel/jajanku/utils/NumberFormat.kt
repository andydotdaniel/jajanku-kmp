package com.andydotdaniel.jajanku.utils

actual class NumberFormatter {
    private val decimalFormat = java.text.DecimalFormat("#,##0.00")
    actual fun format(value: Double): String = decimalFormat.format(value)
}

