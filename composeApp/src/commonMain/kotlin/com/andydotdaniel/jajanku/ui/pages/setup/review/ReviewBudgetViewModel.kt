package com.andydotdaniel.jajanku.ui.pages.setup.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.data.repository.IncomeRepository
import com.andydotdaniel.jajanku.domain.BudgetPlan
import com.andydotdaniel.jajanku.ui.components.GaugeData
import com.andydotdaniel.jajanku.utils.NumberFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReviewBudgetViewModel(
    private val incomeRepository: IncomeRepository,
    private val budgetPlanRepository: BudgetPlanRepository
): ViewModel() {

    data class UIState(
        val selectedBudgetView: Int = 0,
        val spendingBudget: String = "",
        val savings: String = "",
        val needs: GaugeData = GaugeData("", "", 0f),
        val wants: GaugeData = GaugeData("", "", 0f)
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val numberFormatter = NumberFormatter()

    init {
        viewModelScope.launch {
            val incomeFlow = incomeRepository.getIncome()
            val budgetPlanFlow = budgetPlanRepository.getSavedBudgetPlan()

            val dataFlow = combine(incomeFlow, budgetPlanFlow) { income, budgetPlan ->
                income to budgetPlan
            }
            val result = dataFlow.first()
            setData(result.first, result.second!!)
        }
    }

    private fun setData(income: Double, budgetPlan: BudgetPlan) {
        val savings = income * budgetPlan.savings / 100
        val wants = income * budgetPlan.wants / 100
        val needs = income * budgetPlan.needs / 100
        val spendingBudget = wants + needs

        val needsGaugeData = GaugeData("Needs", numberFormatter.format(needs), (needs / spendingBudget).toFloat())
        val wantsGaugeData = GaugeData("Wants", numberFormatter.format(wants), (wants / spendingBudget).toFloat())

        _uiState.value = _uiState.value.copy(
            spendingBudget = numberFormatter.format(spendingBudget),
            savings = numberFormatter.format(savings),
            wants = wantsGaugeData,
            needs = needsGaugeData
        )
    }


    fun selectBudgetView(index: Int) {
        _uiState.value = _uiState.value.copy(selectedBudgetView = index)
    }

}