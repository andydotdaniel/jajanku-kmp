package com.andydotdaniel.jajanku.ui.pages.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao
import com.andydotdaniel.jajanku.data.repository.ExpenseTypeRepository
import com.andydotdaniel.jajanku.ui.components.sheets.ExpenseTypeViewItem
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.NumberInputSanitizer
import com.andydotdaniel.jajanku.utils.parseSerializableExpenseTypeTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddExpenseScreenViewModel(expenseTypeRepository: ExpenseTypeRepository): ViewModel() {

    private val maximumIncomeLength = 11

    data class UIState(
        val expenseAmount: String = "",
        val notes: String = "",
        val expenseTypes: List<ExpenseTypeViewItem> = emptyList(),
        val isExpenseTypeSheetOpen: Boolean = false
    )
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    val numberFormatter = NumberFormatter()
    val numberInputSanitizer = NumberInputSanitizer(numberFormatter.decimalSeparator)

    init {
        viewModelScope.launch {
            val expenseTypes = expenseTypeRepository.getExpenseTypes()
            _uiState.value = _uiState.value.copy(
                expenseTypes = expenseTypes.map { expenseType ->
                    ExpenseTypeViewItem(
                        id = expenseType.uid.toString(),
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

    fun onExpenseTypeSelected(id: String) {
        TODO("implement onExpenseTypeSelected function")
    }

}