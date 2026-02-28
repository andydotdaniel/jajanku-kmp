package com.andydotdaniel.jajanku.ui.screens.setup.plan

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.domain.BudgetPlan
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class BudgetPlanViewData(val name: String, val description: String)

sealed class BudgetPlanSetupEvent {
    class NavigateToReviewBudget(val budgetPlan: BudgetPlan) : BudgetPlanSetupEvent()
}

class BudgetPlanSetupViewModel(
    private val repository: BudgetPlanRepository
): ScreenModel {

    val defaultBudgetPlans = listOf<BudgetPlanViewData>(
        BudgetPlanViewData("50 / 30 / 20", "The most popular budgeting formula"),
        BudgetPlanViewData("70 / 20 / 10", "For those who need to spend more"),
        BudgetPlanViewData("30 / 20 / 50", "For the aggressive savers")
    )

    data class UIState(
        val selectedBudgetPlan: String = ""
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<BudgetPlanSetupEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        screenModelScope.launch {
            repository.getSavedBudgetPlan().collect { plan ->
                if (plan != null) {
                    _uiState.value = _uiState.value.copy(selectedBudgetPlan = plan.id)
                }
            }
        }
    }

    fun submit() {
        screenModelScope.launch {
            val selectedBudgetPlan = _uiState.value.selectedBudgetPlan
            val budgetParts = defaultBudgetPlans[selectedBudgetPlan.toInt()].name.split(" / ")
            val budgetPlan = BudgetPlan(
                selectedBudgetPlan,
                budgetParts[0].toFloat() / 100,
                budgetParts[1].toFloat() / 100,
                budgetParts[2].toFloat() / 100
            )

            repository.saveBudgetPlan(budgetPlan)

            _uiEvents.trySend(BudgetPlanSetupEvent.NavigateToReviewBudget(budgetPlan))
        }
    }

    fun selectBudgetPlan(id: String) {
        _uiState.value = _uiState.value.copy(selectedBudgetPlan = id)
    }

}