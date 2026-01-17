package com.andydotdaniel.jajanku.ui.pages.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.andydotdaniel.jajanku.ui.components.ButtonIcon
import com.andydotdaniel.jajanku.ui.components.PrimaryButton
import com.andydotdaniel.jajanku.ui.components.TextCard
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.chevron_right
import jajanku.composeapp.generated.resources.icon_chevron_right
import org.jetbrains.compose.ui.tooling.preview.Preview

data class BudgetPlan(val name: String, val description: String)

class BudgetPlanSetup(): Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val budgetPlans = listOf<BudgetPlan>(
            BudgetPlan("50 / 30 / 20", "The most popular budgeting formula"),
            BudgetPlan("70 / 20 / 10", "For those who need to spend more"),
            BudgetPlan("30 / 20 / 50", "For the aggressive savers")
        )

        Column(modifier = Modifier.platformSafeContentPadding()) {
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
                budgetPlans.forEach { budgetPlan ->
                    TextCard(budgetPlan.name, budgetPlan.description, modifier = Modifier.padding(top = 16.dp))
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.End) {
                PrimaryButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { navigator.push(ReviewBudget()) },
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
fun BudgetPlanSetupPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            BudgetPlanSetup().Content()
        }
    }
}