package com.andydotdaniel.jajanku.ui.screens.expense

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.andydotdaniel.jajanku.data.repository.ExpenseRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseTypeRepository
import com.andydotdaniel.jajanku.ui.components.sheets.ExpenseTypeViewItem
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.NumberInputSanitizer
import com.andydotdaniel.jajanku.utils.parseSerializableExpenseTypeTitle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class ExpenseInputScreenEvent {
    class ExpenseSaved() : ExpenseInputScreenEvent()
}

class ExpenseInputScreenViewModel(
    expenseTypeRepository: ExpenseTypeRepository,
    private val expenseRepository: ExpenseRepository
): ScreenModel {

    private val maximumIncomeLength = 11

    data class UIState(
        val expenseAmount: String = "",
        val notes: String = "",
        val expenseTypes: List<ExpenseTypeViewItem> = emptyList(),
        val selectedExpenseTypeId: Int? = null,
        val isExpenseTypeSheetOpen: Boolean = false
    ) {
        val isSaveButtonEnabled: Boolean
            get() {
                val amountIsValid = (expenseAmount.toDoubleOrNull() ?: 0.0) > 0.0
                val typeIsSelected = selectedExpenseTypeId != null
                return amountIsValid && typeIsSelected
            }
    }

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<ExpenseInputScreenEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    val numberFormatter = NumberFormatter()
    val numberInputSanitizer = NumberInputSanitizer(numberFormatter.decimalSeparator)

    init {
        screenModelScope.launch {
            val expenseTypes = expenseTypeRepository.getExpenseTypes()
            _uiState.value = _uiState.value.copy(
                expenseTypes = expenseTypes.map { expenseType ->
                    ExpenseTypeViewItem(
                        id = expenseType.uid,
                        title = expenseType.icon + " " + parseSerializableExpenseTypeTitle(expenseType.titles)
                    )
                }
            )
        }
    }

    fun updateExpenseAmount(value: String) {
        if (value.length <= maximumIncomeLength) {
            val sanitizedInput = numberInputSanitizer.sanitize(value)
            val doubleSanitizedInput = sanitizedInput.toDoubleOrNull()

            if ((doubleSanitizedInput != null && doubleSanitizedInput > 0) || sanitizedInput.isEmpty()) {
                _uiState.value = _uiState.value.copy(expenseAmount = sanitizedInput)
            }
        }
    }

    fun updateNotes(text: String) {
        _uiState.value = _uiState.value.copy(notes = text)
    }

    fun showExpenseTypeSheet(show: Boolean) {
        _uiState.value = uiState.value.copy(
            isExpenseTypeSheetOpen = show
        )
    }

    fun onExpenseTypeSelected(id: Int) {
        _uiState.value = uiState.value.copy(
            selectedExpenseTypeId = id
        )
    }

    fun onSaveButtonPressed() {
        screenModelScope.launch {
            expenseRepository.addExpense(
                uiState.value.expenseAmount.toDouble(),
                uiState.value.selectedExpenseTypeId!!,
                uiState.value.notes
            )

            _uiEvents.trySend(ExpenseInputScreenEvent.ExpenseSaved())
        }
    }

}