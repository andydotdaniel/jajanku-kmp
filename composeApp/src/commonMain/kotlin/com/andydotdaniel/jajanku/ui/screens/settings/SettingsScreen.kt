package com.andydotdaniel.jajanku.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.andydotdaniel.jajanku.ui.components.NavigableListItem
import com.andydotdaniel.jajanku.ui.screens.setup.income.IncomeSetup
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview

class SettingsScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(modifier = Modifier.platformSafeContentPadding()) {
            LazyColumn {
                item {
                    Text(
                        "Settings",
                        color = AppColor.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 48.dp, bottom = 24.dp)
                    )
                }

                item {
                    NavigableListItem("Edit Budget", {
                        navigator.push(IncomeSetup())
                    })
                    HorizontalDivider(thickness = 1.dp, color = AppColor.BackgroundGray)
                }

                item {
                    Column(modifier = Modifier.padding(top = 32.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Help us make Jajanku work for you.",
                            color = AppColor.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Give us feedback",
                            color = AppColor.PrimaryActive,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview
fun Preview_SettingsScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = AppColor.Black) {
        SettingsScreen().Content()
    }

}