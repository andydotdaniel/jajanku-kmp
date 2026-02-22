package com.andydotdaniel.jajanku.utils

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
actual class NumberFormatter actual constructor(decimalPlaces: Int) {
    private val formatter = NSNumberFormatter().apply {
        numberStyle = platform.Foundation.NSNumberFormatterCurrencyStyle
        maximumFractionDigits = decimalPlaces.toULong()
        minimumFractionDigits = 0u
    }

    actual fun format(value: Double): String {
        return formatter.stringFromNumber(NSNumber(value))!!
    }

    actual val decimalSeparator: Char
        get() {
            return formatter.decimalSeparator.first()
        }

}
