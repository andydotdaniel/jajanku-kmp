package com.andydotdaniel.jajanku.ui.pages.setup.income

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.andydotdaniel.jajanku.ui.components.ButtonIcon
import com.andydotdaniel.jajanku.ui.components.PrimaryButton
import com.andydotdaniel.jajanku.ui.components.TextInput
import com.andydotdaniel.jajanku.ui.pages.setup.plan.BudgetPlanSetup
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import com.andydotdaniel.jajanku.utils.NumberFormatterVisualTransformation
import jajanku.composeapp.generated.resources.Res
import org.jetbrains.compose.ui.tooling.preview.Preview

import jajanku.composeapp.generated.resources.chevron_right_24px
import jajanku.composeapp.generated.resources.icon_chevron_right_24px
import org.koin.compose.koinInject

class IncomeSetup: Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<IncomeSetupViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = Unit) {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is IncomeSetupEvent.NavigateToBudgetPlanSetup -> {
                        navigator.push(BudgetPlanSetup(event.income))
                    }
                }
            }
        }

        val uiState by viewModel.uiState.collectAsState()

        Column(modifier = Modifier.platformSafeContentPadding()) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Getting Started - 1/3", color = AppColor.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
            Column(Modifier.padding(top = 68.dp)) {
                Text(
                    "What is your monthly income?",
                    color = AppColor.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                )
                TextInput(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    placeholderText = "(e.g. 83,000,000)",
                    value = uiState.income,
                    onValueChange = { viewModel.updateIncome(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    visualTransformation = NumberFormatterVisualTransformation()
                )
                Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalArrangement = Arrangement.End) {
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
}

@Preview(showBackground = true)
@Composable
fun IncomeSetupPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            IncomeSetup().Content()
        }
    }
}