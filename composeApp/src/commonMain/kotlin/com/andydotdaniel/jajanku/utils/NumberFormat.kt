package com.andydotdaniel.jajanku.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

expect class NumberFormatter() {
    fun format(value: Double): String
}

class NumberFormatterVisualTransformation() : VisualTransformation {

    private val formatter = NumberFormatter()

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val numberValue = originalText.toDoubleOrNull()

        if (originalText.isEmpty() || numberValue == null) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val formattedText = formatter.format(numberValue)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // This is a simplified mapping. For a perfect cursor position,
                // a more complex mapping is needed that accounts for separator insertions/deletions.
                // However, this approach moves the cursor to the end, which is often acceptable.
                return formattedText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                // Likewise, this moves the cursor to the end of the unformatted text.
                return originalText.length
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }
}
