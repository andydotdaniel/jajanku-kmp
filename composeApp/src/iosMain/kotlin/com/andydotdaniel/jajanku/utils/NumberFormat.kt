package com.andydotdaniel.jajanku.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
actual class NumberFormatter {
    private val formatter = NSNumberFormatter().apply {
        numberStyle = platform.Foundation.NSNumberFormatterCurrencyStyle
        maximumFractionDigits = 2u
        minimumFractionDigits = 0u
    }
    actual fun format(value: Double): String =
        formatter.stringFromNumber(NSNumber(value)) ?: ""
}
