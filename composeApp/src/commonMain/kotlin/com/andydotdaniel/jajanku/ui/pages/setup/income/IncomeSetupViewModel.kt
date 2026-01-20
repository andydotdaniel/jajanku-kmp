package com.andydotdaniel.jajanku.ui.pages.setup.income

import androidx.lifecycle.ViewModel
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.NumberInputSanitizer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

sealed class IncomeSetupEvent {
    class NavigateToBudgetPlanSetup() : IncomeSetupEvent()
}

class IncomeSetupViewModel: ViewModel() {

    private val maximumIncomeLength = 11

    data class UIState(
        val income: String = ""
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<IncomeSetupEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    val numberFormatter = NumberFormatter()
    val numberInputSanitizer = NumberInputSanitizer(numberFormatter.decimalSeparator)

    fun updateIncome(value: String) {
        if (value.length <= maximumIncomeLength) {
            val sanitizedInput = numberInputSanitizer.sanitize(value)

            if (sanitizedInput.toDoubleOrNull() != null || value.isEmpty()) {
                _uiState.value = _uiState.value.copy(income = sanitizedInput)
            }
        }
    }

    fun submit() {
        _uiEvents.trySend(IncomeSetupEvent.NavigateToBudgetPlanSetup())
    }

}