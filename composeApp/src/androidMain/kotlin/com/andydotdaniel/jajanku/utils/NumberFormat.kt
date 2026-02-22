package com.andydotdaniel.jajanku.utils

import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

actual class NumberFormatter {
    private val decimalFormat = NumberFormat.getCurrencyInstance().apply {
        minimumFractionDigits = 0
        isGroupingUsed = true
    }
    actual fun format(value: Double, decimalPlaces: Int): String {
        decimalFormat.apply { maximumFractionDigits = decimalPlaces }
        return decimalFormat.format(value)
    }

    private val locale = Locale.getDefault()
    private val symbols = DecimalFormatSymbols.getInstance(locale)
    actual val decimalSeparator: Char
        get() {
            return symbols.decimalSeparator
        }
}

