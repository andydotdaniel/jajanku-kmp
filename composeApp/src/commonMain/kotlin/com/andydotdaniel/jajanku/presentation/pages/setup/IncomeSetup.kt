package com.andydotdaniel.jajanku.presentation.pages.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andydotdaniel.jajanku.presentation.components.ButtonIcon
import com.andydotdaniel.jajanku.presentation.components.PrimaryButton
import com.andydotdaniel.jajanku.presentation.components.TextInput
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import org.jetbrains.compose.ui.tooling.preview.Preview

import jajanku.composeapp.generated.resources.chevron_right
import jajanku.composeapp.generated.resources.icon_chevron_right

@Composable
fun IncomeSetup() {
    Column(Modifier.padding(horizontal = 24.dp)) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Getting Started - 1/3", color = AppColor.White, fontSize = 14.sp)
        }
        Column(Modifier.padding(top = 68.dp)) {
            Text(
                "What is your monthly income?",
                color = AppColor.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp
            )
            TextInput(modifier = Modifier.fillMaxWidth().padding(top = 24.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {},
                    text = "Continue",
                    icon = ButtonIcon(
                        icon = Res.drawable.chevron_right,
                        contentDescription = Res.string.icon_chevron_right
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeSetupPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            IncomeSetup()
        }
    }
}