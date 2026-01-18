package com.andydotdaniel.jajanku.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
actual class NumberFormatter {
    private val formatter = NSNumberFormatter().apply {
        numberStyle = platform.Foundation.NSNumberFormatterDecimalStyle
    }
    actual fun format(value: Double): String =
        formatter.stringFromNumber(NSNumber(value)) ?: ""
}
