package com.andydotdaniel.jajanku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import jajanku.composeapp.generated.resources.chevron_down_24px
import jajanku.composeapp.generated.resources.icon_chevron_down_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DropdownPicker(
    modifier: Modifier = Modifier,
    selectionText: String? = null,
    placeholderText: String,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .clickable(onClick = onClick)
        .background(color = AppColor.BackgroundGray, shape = RoundedCornerShape(16.dp))
        .border(
            width = 1.dp,
            color = AppColor.PlaceholderGray,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (selectionText.isNullOrEmpty()) {
                Text(
                    text = placeholderText,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColor.PlaceholderGray,
                    fontSize = 21.sp
                )
            } else {
                Text(
                    text = selectionText,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColor.White,
                    fontSize = 21.sp
                )
            }
            Icon(
                painter = painterResource(Res.drawable.chevron_down_24px),
                contentDescription = stringResource(resource = Res.string.icon_chevron_down_24px),
                tint = AppColor.PlaceholderGray
            )
        }
    }
}

@Composable
@Preview
fun Preview_DropdownPicker() {
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
                DropdownPicker(
                    modifier = Modifier.fillMaxWidth(),
                    placeholderText = "Select Category",
                    onClick = {}
                )
            }
        }
    }
}
