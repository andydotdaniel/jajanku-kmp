package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.chevron_right_24px
import jajanku.composeapp.generated.resources.icon_chevron_right_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ButtonIcon(
    val icon: DrawableResource,
    val contentDescription: StringResource
)
@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: ButtonIcon? = null
) {
    val contentPadding: PaddingValues = if (icon != null) PaddingValues(start = 24.dp, top = 12.dp, bottom = 12.dp, end = 14.dp) else PaddingValues(vertical = 12.dp, horizontal = 24.dp)

    Button(
        modifier = modifier,
        contentPadding = contentPadding,
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColor.PrimaryActive,
            disabledContainerColor = AppColor.BackgroundGray
        ),
    ) {
        Row() {
            Text(text = text, fontWeight = FontWeight.SemiBold, color = AppColor.Black, fontSize = 18.sp)

            if (icon != null) {
                Icon(
                    painter = painterResource(icon.icon),
                    contentDescription = stringResource(resource = icon.contentDescription),
                    tint = AppColor.Black,
                    modifier = Modifier.absoluteOffset(y = (-0.5).dp)
                )
            }
        }
    }
}

@Preview()
@Composable
fun PrimaryButtonPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF1E1E1E)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PrimaryButton(onClick = {}, text = "Continue")
                PrimaryButton(onClick = {}, text = "Continue", icon = ButtonIcon(
                    icon = Res.drawable.chevron_right_24px,
                    contentDescription = Res.string.icon_chevron_right_24px
                ))
            }
        }
    }
}