package com.andydotdaniel.jajanku.utils

class NumberInputSanitizer(val decimalSeparator: Char) {

    fun sanitize(text: String): String {
        val decimalAdjustedText = if (decimalSeparator == ',') text.replace(',', '.') else text

        if (decimalAdjustedText.contains('.')) {
            val beforeDecimal = decimalAdjustedText.substringBefore('.')
            val afterDecimal = decimalAdjustedText.substringAfter('.')

            // Combine the parts, limiting the after-decimal part to 2 digits
            return beforeDecimal + '.' + afterDecimal.take(2)
        }

        return decimalAdjustedText
    }

}