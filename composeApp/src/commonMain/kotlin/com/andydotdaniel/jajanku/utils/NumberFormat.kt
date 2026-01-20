package com.andydotdaniel.jajanku.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

expect class NumberFormatter() {
    fun format(value: Double): String

    val decimalSeparator: Char
}

class NumberFormatterVisualTransformation() : VisualTransformation {

    private val formatter = NumberFormatter()

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        // NOTE: Ensure that the original text is a valid double before formatting.
        // App is set to intentionally crash otherwise to catch these errors early.
        val formattedText = formatter.format(originalText.toDouble())

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
