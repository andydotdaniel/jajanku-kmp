package com.andydotdaniel.jajanku.ui.pages.setup.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.andydotdaniel.jajanku.domain.BudgetPlan
import com.andydotdaniel.jajanku.ui.components.GaugeData
import com.andydotdaniel.jajanku.ui.components.GroupedGauge
import com.andydotdaniel.jajanku.ui.components.PrimaryButton
import com.andydotdaniel.jajanku.ui.components.SegmentedPillControl
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class ReviewBudget(private val income: Double, private val budgetPlan: BudgetPlan): Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<ReviewBudgetViewModel>(
            parameters = { parametersOf(income, budgetPlan) }
        )
        val uiState by viewModel.uiState.collectAsState()

        Column(Modifier.platformSafeContentPadding()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Review Budget - 3/3", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(top = 68.dp)
            ) {
                Text("Spending Budget", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(viewModel.uiState.value.spendingBudget, color = AppColor.White, fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    Text("\uD83D\uDCB0 Savings", color = AppColor.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("+ ${viewModel.uiState.value.savings}", color = AppColor.PrimaryActive, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(top = 56.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                val options = listOf("Monthly", "Weekly", "Daily")
                SegmentedPillControl(options, uiState.selectedBudgetView) {
                    viewModel.selectBudgetView(it)
                }

                val gaugeData = listOf<GaugeData>(
                    viewModel.uiState.value.needs,
                    viewModel.uiState.value.wants
                )
                GroupedGauge(gaugeData)
            }

            PrimaryButton(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                text = "Save Budget",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewBudgetPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            ReviewBudget(12000.0, BudgetPlan("0", 50, 30, 20)).Content()
        }
    }
}