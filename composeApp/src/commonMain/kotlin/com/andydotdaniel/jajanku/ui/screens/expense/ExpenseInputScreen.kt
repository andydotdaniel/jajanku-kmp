package com.andydotdaniel.jajanku.ui.screens.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.andydotdaniel.jajanku.di.getScreenModel
import com.andydotdaniel.jajanku.ui.components.DropdownPicker
import com.andydotdaniel.jajanku.ui.components.PrimaryButton
import com.andydotdaniel.jajanku.ui.components.TextInput
import com.andydotdaniel.jajanku.ui.components.sheets.ExpenseTypeBottomSheet
import com.andydotdaniel.jajanku.ui.navigation.ModalScreen
import com.andydotdaniel.jajanku.ui.platformSafeContentPadding
import com.andydotdaniel.jajanku.utils.AppColor
import com.andydotdaniel.jajanku.utils.NumberFormatterVisualTransformation
import jajanku.composeapp.generated.resources.Res
import jajanku.composeapp.generated.resources.close_24px
import jajanku.composeapp.generated.resources.icon_close_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class ExpenseInputScreen(private val onExpenseSavedCallback: () -> Unit): Screen, ModalScreen {

    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.Black
        ) {
            val navigator = LocalNavigator.currentOrThrow
            val viewModel = getScreenModel<ExpenseInputScreenViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(key1 = Unit) {
                viewModel.uiEvents.collect { event ->
                    when (event) {
                        is ExpenseInputScreenEvent.ExpenseSaved -> {
                            navigator.pop()
                            onExpenseSavedCallback()
                        }
                    }
                }
            }

            if (uiState.isExpenseTypeSheetOpen) {
                ExpenseTypeBottomSheet(
                    expenseTypes = uiState.expenseTypes,
                    onExpenseTypeSelected = { viewModel.onExpenseTypeSelected(it) },
                    onDismiss = { viewModel.showExpenseTypeSheet(false) }
                )
            }

            Column(modifier = Modifier.platformSafeContentPadding()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navigator.pop() },
                        modifier = Modifier
                            .size(36.dp) // Set a fixed size
                            .clip(CircleShape) // Clip the button to a circle
                            .background(AppColor.BackgroundGray) // Set the background color
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.close_24px),
                            contentDescription = stringResource(Res.string.icon_close_24px), // Essential for accessibility
                            tint = AppColor.White // Set the icon's color
                        )
                    }
                    Text(
                        "Add Expense",
                        color = AppColor.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(16.dp))
                }

                Column {
                    TextInput(
                        modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                        placeholderText = "Expense Amount",
                        value = uiState.expenseAmount,
                        onValueChange = { viewModel.updateExpenseAmount(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        visualTransformation = NumberFormatterVisualTransformation()
                    )
                    DropdownPicker(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        selectionText = if (uiState.selectedExpenseTypeId != null) uiState.expenseTypes.find { it.id == uiState.selectedExpenseTypeId }?.title else null,
                        placeholderText = "Select Category",
                        onClick = { viewModel.showExpenseTypeSheet(true) }
                    )
                    TextInput(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        placeholderText = "Notes (Optional)",
                        value = uiState.notes,
                        onValueChange = { viewModel.updateNotes(it) },
                        singleLine = false
                    )
                    PrimaryButton(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        text = "Save Expense",
                        onClick = {
                            viewModel.onSaveButtonPressed()
                        },
                        enabled = uiState.isSaveButtonEnabled
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun Preview_AddExpenseScreen() {
    ExpenseInputScreen({}).Content()
}