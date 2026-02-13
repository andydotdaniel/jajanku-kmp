package com.andydotdaniel.jajanku.ui.pages.setup.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andydotdaniel.jajanku.data.repository.IncomeRepository
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.NumberInputSanitizer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class IncomeSetupEvent {
    class NavigateToBudgetPlanSetup(val income: Double) : IncomeSetupEvent()
}

class IncomeSetupViewModel(private val incomeRepository: IncomeRepository): ViewModel() {

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

    init {
        viewModelScope.launch {
            incomeRepository.getIncome().collect { value ->
                if (value != null) {
                    updateIncome(value.toString())
                }
            }
        }
    }

    fun updateIncome(value: String) {
        if (value.length <= maximumIncomeLength) {
            val sanitizedInput = numberInputSanitizer.sanitize(value)
            val doubleSanitizedInput = sanitizedInput.toDoubleOrNull()

            if ((doubleSanitizedInput != null && doubleSanitizedInput > 0) || sanitizedInput.isEmpty()) {
                _uiState.value = _uiState.value.copy(income = sanitizedInput)
            }
        }
    }

    fun submit() {
        viewModelScope.launch {
            val doubleIncome = uiState.value.income.toDouble()
            incomeRepository.updateIncome(doubleIncome)
            _uiEvents.trySend(IncomeSetupEvent.NavigateToBudgetPlanSetup(doubleIncome))
        }
    }

}