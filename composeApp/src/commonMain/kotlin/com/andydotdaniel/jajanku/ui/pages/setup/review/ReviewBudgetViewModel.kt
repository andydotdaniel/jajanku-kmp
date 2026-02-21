package com.andydotdaniel.jajanku.ui.pages.setup.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.data.repository.IncomeRepository
import com.andydotdaniel.jajanku.domain.Budget
import com.andydotdaniel.jajanku.domain.BudgetPlan
import com.andydotdaniel.jajanku.ui.components.GaugeData
import com.andydotdaniel.jajanku.utils.NumberFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReviewBudgetViewModel(income: Double, budgetPlan: BudgetPlan): ViewModel() {

    enum class BudgetView(val value : Int) {
        MONTHLY(0),
        WEEKLY(1),
        DAILY(2);

        companion object {
            fun from(findValue: Int): BudgetView = entries.first { it.value == findValue }
        }
    }

    private val numberFormatter = NumberFormatter()

    data class UIState(
        val selectedBudgetView: Int = 0,
        val spendingBudget: String,
        val savings: String,
        val needs: GaugeData,
        val wants: GaugeData
    )

    val budget = Budget(income, budgetPlan)
    val savings = budget.savingsAmount
    val wants = budget.wantsAmount

    val needs = budget.needsAmount
    val spendingBudget = wants + needs
    val needsGaugeData = GaugeData("Needs", numberFormatter.format(needs), (needs / spendingBudget).toFloat())
    val wantsGaugeData = GaugeData("Wants", numberFormatter.format(wants), (wants / spendingBudget).toFloat())
    private val _uiState = MutableStateFlow(UIState(
        spendingBudget = numberFormatter.format(spendingBudget),
        savings = numberFormatter.format(savings),
        wants = wantsGaugeData,
        needs = needsGaugeData
    ))
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun selectBudgetView(index: Int) {
        val budgetView = BudgetView.from(index)
        val budgetViewRatio = when (budgetView) {
            BudgetView.MONTHLY -> 1.0
            BudgetView.WEEKLY -> 4.0
            BudgetView.DAILY -> 30.0
        }

        _uiState.value = _uiState.value.copy(
            selectedBudgetView = index,
            spendingBudget = numberFormatter.format(spendingBudget / budgetViewRatio),
            savings = numberFormatter.format(savings / budgetViewRatio),
            wants = wantsGaugeData.copy(value = numberFormatter.format(wants / budgetViewRatio)),
            needs = needsGaugeData.copy(value = numberFormatter.format(needs / budgetViewRatio))
        )
    }

}