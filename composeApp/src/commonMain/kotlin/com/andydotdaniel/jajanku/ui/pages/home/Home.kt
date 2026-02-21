package com.andydotdaniel.jajanku.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.andydotdaniel.jajanku.ui.components.ExpenseItem
import com.andydotdaniel.jajanku.ui.components.ExpenseListItem
import com.andydotdaniel.jajanku.ui.components.GroupedGauge
import com.andydotdaniel.jajanku.ui.components.PrimaryButton
import com.andydotdaniel.jajanku.ui.components.SegmentedPillControl
import com.andydotdaniel.jajanku.ui.pages.expense.AddExpenseScreen
import com.andydotdaniel.jajanku.ui.pages.settings.SettingsScreen
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.icon_settings_24px
import jajanku.composeapp.generated.resources.settings_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class Home: Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        Scaffold (
            topBar = {
                Row(modifier = Modifier.platformSafeContentPadding().fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = { navigator.push(SettingsScreen()) },
                        modifier = Modifier
                            .size(36.dp) // Set a fixed size
                            .clip(CircleShape) // Clip the button to a circle
                            .background(AppColor.BackgroundGray) // Set the background color
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.settings_24px),
                            contentDescription = stringResource(Res.string.icon_settings_24px), // Essential for accessibility
                            tint = AppColor.White // Set the icon's color
                        )
                    }
                }
            },
            containerColor = AppColor.Black,
            content = {
                val data = uiState.expenseItems

                LazyColumn(modifier = Modifier.platformSafeContentPadding().padding(top = 72.dp)) {
                    item {
                        Text("Remaining Budget", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text(uiState.remainingBudget, color = AppColor.White, fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))

                        val options = uiState.budgetViewMenuOptions
                        SegmentedPillControl(modifier = Modifier.padding(top = 24.dp), options = options, uiState.selectedBudgetView) {
                            viewModel.selectBudgetView(it)
                        }
                    }

                    if (uiState.needs != null && uiState.wants != null) {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))

                            val gaugeData = listOf(uiState.needs!!, uiState.wants!!)
                            GroupedGauge(gaugeData)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(48.dp))
                        Text("Today's Expenses", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(count = data.size) { index ->
                        val item = data[index]

                        ExpenseListItem(item, false) {}
                        if (index < data.size - 1) {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(thickness = 1.dp, color = AppColor.BackgroundGray)
                            Spacer(modifier = Modifier.height(16.dp))
                        }  else {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            },

            floatingActionButton = {
                PrimaryButton(text = "Add Expense", onClick = {
                    navigator.push(AddExpenseScreen())
                })
            }
        )
    }

}

@Preview
@Composable
fun PreviewHomeScreen() {
    Scaffold (
        topBar = {
            Row(modifier = Modifier.platformSafeContentPadding().fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = { /* Handle click */ },
                    modifier = Modifier
                        .size(36.dp) // Set a fixed size
                        .clip(CircleShape) // Clip the button to a circle
                        .background(AppColor.BackgroundGray) // Set the background color
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.settings_24px),
                        contentDescription = stringResource(Res.string.icon_settings_24px), // Essential for accessibility
                        tint = AppColor.White // Set the icon's color
                    )
                }
            }
        },
        containerColor = AppColor.Black,
        content = {
            val data = listOf<ExpenseItem>(
                ExpenseItem(
                    id = 1,
                    icon = "\uD83C\uDF54",
                    amount = "Rp 24103",
                    category = "Food",
                    time = "12:00",
                ),
                ExpenseItem(
                    id = 2,
                    icon = "\uD83C\uDF79",
                    amount = "Rp 12103",
                    category = "Drink",
                    time = "11:00",
                ),
                ExpenseItem(
                    id = 3,
                    icon = "\uD83C\uDF79",
                    amount = "Rp 73103",
                    description = "Some long text about the item description that's long",
                    category = "Drink",
                    time = "09:00",
                )
            )

            LazyColumn(modifier = Modifier.platformSafeContentPadding().padding(top = 64.dp)) {
                item {
                    Text("Remaining Budget", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text("234000", color = AppColor.White, fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))

                    val options = listOf("Monthly", "Weekly", "Daily")
                    SegmentedPillControl(modifier = Modifier.padding(top = 16.dp), options = options, 1) {

                    }
                }

                item {
                    Spacer(modifier = Modifier.height(48.dp))
                    Text("Today's Expenses", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(count = data.size) { index ->
                    val item = data[index]

                    ExpenseListItem(item, false) {}
                    if (index < data.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFF333333))
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        },

        floatingActionButton = {
            PrimaryButton(text = "Add Expense", onClick = {})
        }
    )


}