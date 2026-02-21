package com.andydotdaniel.jajanku.ui.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andydotdaniel.jajanku.data.repository.BudgetRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseRange
import com.andydotdaniel.jajanku.data.repository.ExpenseRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseTypeRepository
import com.andydotdaniel.jajanku.ui.components.ExpenseItem
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.Time
import com.andydotdaniel.jajanku.utils.parseSerializableExpenseTypeTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val expenseTypeRepository: ExpenseTypeRepository,
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository
): ViewModel() {

    data class UIState(
        val selectedBudgetView: Int = 0,
        val remainingBudget: String = "",
        val expenseItems: List<ExpenseItem> = emptyList()
    )
    private val numberFormatter = NumberFormatter()
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<HomeViewModel.UIState> = _uiState.asStateFlow()

    fun selectBudgetView(index: Int) {

    }

    init {
        viewModelScope.launch {
            val expenses = expenseRepository.getExpenses(ExpenseRange.TODAY)
            val budget = budgetRepository.getBudget()
            val remainingBudget = budget.income - budget.budgetPlan.savings
            val formattedRemainingBudget = numberFormatter.format(remainingBudget)

            val expenseTypes = expenseTypeRepository.getExpenseTypes()

            val expenseItems = expenses.map {
                val expenseType = expenseTypes.find { expenseType -> expenseType.uid == it.uid }

                ExpenseItem(
                    id = it.uid,
                    icon = expenseType?.icon ?: "",
                    category = if (expenseType != null) parseSerializableExpenseTypeTitle(expenseType.titles) else "",
                    amount = numberFormatter.format(it.amount),
                    time = Time.formatTimestamp(it.timestamp)
                )
            }

            _uiState.value = uiState.value.copy(
                remainingBudget = formattedRemainingBudget,
                expenseItems = expenseItems
            )
        }

    }


}