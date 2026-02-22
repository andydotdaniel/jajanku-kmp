package com.andydotdaniel.jajanku.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
actual class NumberFormatter {
    private val formatter = NSNumberFormatter().apply {
        numberStyle = platform.Foundation.NSNumberFormatterCurrencyStyle
        minimumFractionDigits = 0u
    }

    actual fun format(value: Double, decimalPlaces: Int): String {
        formatter.apply { maximumFractionDigits = decimalPlaces.toULong() }
        return formatter.stringFromNumber(NSNumber(value))!!
    }

    actual val decimalSeparator: Char
        get() {
            return formatter.decimalSeparator.first()
        }

}
