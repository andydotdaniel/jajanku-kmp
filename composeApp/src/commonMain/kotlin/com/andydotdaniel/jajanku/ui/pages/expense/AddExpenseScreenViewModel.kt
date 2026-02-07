package com.andydotdaniel.jajanku.ui.pages.expense

import androidx.lifecycle.ViewModel
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.NumberInputSanitizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddExpenseScreenViewModel: ViewModel() {

    private val maximumIncomeLength = 11

    data class UIState(
        val expenseAmount: String = "",
        val notes: String = ""
    )
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    val numberFormatter = NumberFormatter()
    val numberInputSanitizer = NumberInputSanitizer(numberFormatter.decimalSeparator)

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

}