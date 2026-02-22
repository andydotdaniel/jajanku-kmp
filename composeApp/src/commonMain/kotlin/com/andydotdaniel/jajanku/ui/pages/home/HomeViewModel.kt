package com.andydotdaniel.jajanku.ui.pages.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.andydotdaniel.jajanku.data.database.entities.Expense
import com.andydotdaniel.jajanku.data.repository.BudgetRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseRange
import com.andydotdaniel.jajanku.data.repository.ExpenseRepository
import com.andydotdaniel.jajanku.data.repository.ExpenseTypeRepository
import com.andydotdaniel.jajanku.ui.components.BudgetView
import com.andydotdaniel.jajanku.ui.components.ExpenseItem
import com.andydotdaniel.jajanku.ui.components.GaugeData
import com.andydotdaniel.jajanku.ui.pages.expense.ExpenseInputScreenEvent
import com.andydotdaniel.jajanku.utils.NumberFormatter
import com.andydotdaniel.jajanku.utils.Time
import com.andydotdaniel.jajanku.utils.parseSerializableExpenseTypeTitle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private data class RemainingBudgetAmounts(
    val remainingBudget: Double,
    val needs: GaugeData,
    val wants: GaugeData
)

class HomeViewModel(
    private val expenseTypeRepository: ExpenseTypeRepository,
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository
): ScreenModel {

    data class UIState(
        val selectedBudgetView: Int = 0,
        val remainingBudget: String = "",
        val budgetViewMenuOptions: List<String> = listOf("Monthly", "Weekly", "Daily"),

        val needs: GaugeData? = null,
        val wants: GaugeData? = null,

        val expenseItems: List<ExpenseItem> = emptyList()
    )

    private val numberFormatter = NumberFormatter()
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<HomeViewModel.UIState> = _uiState.asStateFlow()

    val uiEvents = Channel<ExpenseInputScreenEvent>()

    fun selectBudgetView(index: Int) {
        if (index == _uiState.value.selectedBudgetView) return

        screenModelScope.launch {
            val remainingBudgetAmounts = calculateRemainingBudgetAmounts(index)
            _uiState.value = _uiState.value.copy(
                selectedBudgetView = index,
                remainingBudget = numberFormatter.format(remainingBudgetAmounts.remainingBudget),
                needs = remainingBudgetAmounts.needs,
                wants = remainingBudgetAmounts.wants
            )
        }
    }

    private suspend fun calculateRemainingBudgetAmounts(budgetViewIndex: Int): RemainingBudgetAmounts {
        val budget = budgetRepository.getBudget()

        val expenses = when (budgetViewIndex) {
            0 -> expenseRepository.getExpenses(ExpenseRange.PAST_MONTH)
            1 -> expenseRepository.getExpenses(ExpenseRange.PAST_WEEK)
            2 -> expenseRepository.getExpenses(ExpenseRange.TODAY)
            else -> {
                throw RuntimeException("Invalid budget view index selected")
            }
        }

        val budgetView = BudgetView.from(budgetViewIndex)
        val budgetViewRatio = when (budgetView) {
            BudgetView.MONTHLY -> 1.0
            BudgetView.WEEKLY -> 4.0
            BudgetView.DAILY -> 30.0
        }

        val remainingBudget = budget.spendingBudgetAmount - expenses.sumOf { it.amount }
        val remainingNeedsBudget = remainingBudget * budget.budgetPlan.needs
        val remainingWantsBudget = remainingBudget * budget.budgetPlan.wants

        val needs = GaugeData("Needs", numberFormatter.format(remainingNeedsBudget / budgetViewRatio), (remainingNeedsBudget / remainingBudget).toFloat())
        val wants = GaugeData("Wants", numberFormatter.format(remainingWantsBudget / budgetViewRatio), (remainingWantsBudget / remainingBudget).toFloat())

        return RemainingBudgetAmounts(remainingBudget / budgetViewRatio, needs, wants)
    }

    private suspend fun formatExpenses(expenses: List<Expense>): List<ExpenseItem> {
        val expenseTypes = expenseTypeRepository.getExpenseTypes()
        return expenses.map {
            val expenseType = expenseTypes.find { expenseType -> expenseType.uid == it.type }

            ExpenseItem(
                id = it.uid,
                icon = expenseType?.icon ?: "",
                category = if (expenseType != null) parseSerializableExpenseTypeTitle(expenseType.titles) else "",
                amount = numberFormatter.format(it.amount),
                time = Time.formatTimestamp(it.timestamp),
                description = it.notes
            )
        }
    }

    init {
        screenModelScope.launch {
            val todayExpenses = expenseRepository.getExpenses(ExpenseRange.TODAY)
            val pastMonthExpenses = expenseRepository.getExpenses(ExpenseRange.PAST_MONTH)

            val budget = budgetRepository.getBudget()
            val remainingBudget = budget.spendingBudgetAmount - pastMonthExpenses.sumOf { it.amount }
            val remainingNeedsBudget = remainingBudget * budget.budgetPlan.needs
            val remainingWantsBudget = remainingBudget * budget.budgetPlan.wants

            val needs = GaugeData("Needs", numberFormatter.format(remainingNeedsBudget), (remainingNeedsBudget / remainingBudget).toFloat())
            val wants = GaugeData("Wants", numberFormatter.format(remainingWantsBudget), (remainingWantsBudget / remainingBudget).toFloat())

            val formattedRemainingBudget = numberFormatter.format(remainingBudget)
            val expenseItems = formatExpenses(todayExpenses)

            _uiState.value = uiState.value.copy(
                remainingBudget = formattedRemainingBudget,
                expenseItems = expenseItems,
                needs = needs,
                wants = wants
            )
        }

    }

    fun refreshExpenses() {
        screenModelScope.launch {
            val todayExpenses = expenseRepository.getExpenses(ExpenseRange.TODAY)
            val expenseItems = formatExpenses(todayExpenses)

            val remainingBudgetAmounts = calculateRemainingBudgetAmounts(uiState.value.selectedBudgetView)

            _uiState.value = uiState.value.copy(
                remainingBudget = numberFormatter.format(remainingBudgetAmounts.remainingBudget),
                needs = remainingBudgetAmounts.needs,
                wants = remainingBudgetAmounts.wants,
                expenseItems = expenseItems
            )
        }

    }

}