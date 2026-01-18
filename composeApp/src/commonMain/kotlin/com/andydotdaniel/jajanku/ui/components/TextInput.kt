package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TextInput(
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.SemiBold,
    value: String,
    onValueChange: (String) -> Unit = {},
) {
    TextField(
        modifier = modifier,
        placeholder = { Text(
            "(e.g. 83,000,000)",
            fontWeight = fontWeight,
            color = AppColor.PlaceholderGray,
            fontSize = 21.sp
        ) },
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppColor.BackgroundGray,
            unfocusedContainerColor = AppColor.BackgroundGray,
            disabledContainerColor = AppColor.BackgroundGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
