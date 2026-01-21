package com.andydotdaniel.jajanku.ui.pages.setup.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

sealed class BudgetPlanSetupEvent {
    class NavigateToReviewBudget() : BudgetPlanSetupEvent()
}

class BudgetPlanSetupViewModel: ViewModel() {

    data class UIState(
        val selectedBudgetPlan: String = ""
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<BudgetPlanSetupEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun submit() {
        _uiEvents.trySend(BudgetPlanSetupEvent.NavigateToReviewBudget())
    }

    fun selectBudgetPlan(id: String) {
        _uiState.value = _uiState.value.copy(selectedBudgetPlan = id)
    }

}