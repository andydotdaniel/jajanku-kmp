package com.andydotdaniel.jajanku.ui.pages.setup.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.andydotdaniel.jajanku.ui.components.ButtonIcon
import com.andydotdaniel.jajanku.ui.components.PrimaryButton
import com.andydotdaniel.jajanku.ui.components.TextCard
import com.andydotdaniel.jajanku.ui.pages.setup.review.ReviewBudget
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.chevron_right_24px
import jajanku.composeapp.generated.resources.icon_chevron_right_24px
import org.jetbrains.compose.ui.tooling.preview.Preview

class BudgetPlanSetup(private val income: Double): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<BudgetPlanSetupViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is BudgetPlanSetupEvent.NavigateToReviewBudget -> {
                        navigator.push(ReviewBudget(income, event.budgetPlan))
                    }
                }
            }
        }

        val scrollState = rememberScrollState()
        Column(modifier = Modifier.platformSafeContentPadding().verticalScroll(scrollState)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Select Budget - 2/3", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            Column(Modifier.padding(top = 68.dp)) {
                Text(
                    "Select your budget plan.",
                    color = AppColor.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                )
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco",
                    color = AppColor.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 21.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Column(Modifier.padding(top = 54.dp)) {
                Text(
                    "Needs / Wants / Savings",
                    color = AppColor.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )
                viewModel.defaultBudgetPlans.forEachIndexed { index, budgetPlan ->
                    val id = "$index"

                    TextCard(
                        budgetPlan.name,
                        budgetPlan.description,
                        id = id,
                        onClick = { viewModel.selectBudgetPlan(it) },
                        selected = uiState.selectedBudgetPlan == id,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { viewModel.submit() },
                    text = "Continue",
                    icon = ButtonIcon(
                        icon = Res.drawable.chevron_right_24px,
                        contentDescription = Res.string.icon_chevron_right_24px
                    )
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BudgetPlanSetupPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            BudgetPlanSetup(income = 100000.0).Content()
        }
    }
}