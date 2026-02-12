package com.andydotdaniel.jajanku.ui.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

data class ExpenseTypeViewItem(
    val id: Int,
    val title: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTypeBottomSheet(
    expenseTypes: List<ExpenseTypeViewItem>,
    onExpenseTypeSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        LazyColumn {
            items(expenseTypes) { category ->
                ListItem(
                    headlineContent = { Text(category.title) },
                    modifier = Modifier.clickable {
                        onExpenseTypeSelected(category.id)
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                            }
                        }
                    }
                )
            }
        }
    }
}