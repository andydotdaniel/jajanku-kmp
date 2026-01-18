package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TextInput(
    modifier: Modifier = Modifier,
    placeholderText: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier,
        placeholder = { Text(
            placeholderText,
            fontWeight = FontWeight.SemiBold,
            color = AppColor.PlaceholderGray,
            fontSize = 21.sp
        ) },
        textStyle = TextStyle(
            color = AppColor.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppColor.BackgroundGray,
            unfocusedContainerColor = AppColor.BackgroundGray,
            disabledContainerColor = AppColor.BackgroundGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = AppColor.White,
            unfocusedTextColor = AppColor.White
        ),
        visualTransformation = visualTransformation
    )
}
