package com.andydotdaniel.jajanku.ui.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.andydotdaniel.jajanku.ui.components.ExpenseItem
import com.andydotdaniel.jajanku.ui.components.ExpenseListItem
import com.andydotdaniel.jajanku.ui.components.SegmentedPillControl
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.String

class Home: Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        Column(modifier = Modifier.platformSafeContentPadding()) {
            Column(Modifier.padding(top = 16.dp)) {
                Text("Remaining Budget", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(viewModel.uiState.value.remainingBudget, color = AppColor.White, fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))


            }

            Column(Modifier.padding(top = 16.dp)) {

            }
        }
    }

}

@Preview
@Composable
fun PreviewHomeScreen() {
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

    LazyColumn(modifier = Modifier.platformSafeContentPadding()) {
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
}