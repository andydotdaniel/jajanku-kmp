package com.andydotdaniel.jajanku.ui.pages.setup.income

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

sealed class IncomeSetupEvent {
    class NavigateToBudgetPlanSetup() : IncomeSetupEvent()
}

class IncomeSetupViewModel: ViewModel() {

    data class UIState(
        val income: String = ""
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<IncomeSetupEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun updateIncome(value: String) {
        _uiState.value = _uiState.value.copy(income = value)
    }

    fun submit() {
        println("Income value: ${_uiState.value.income}")
        _uiEvents.trySend(IncomeSetupEvent.NavigateToBudgetPlanSetup())
    }

}