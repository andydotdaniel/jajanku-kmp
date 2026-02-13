package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.domain.Budget
import kotlinx.coroutines.flow.first

interface BudgetRepository {
    suspend fun getBudget(): Budget
}

class AppBudgetRepository(
    private val incomeRepository: IncomeRepository,
    private val budgetPlanRepository: BudgetPlanRepository
): BudgetRepository {

    override suspend fun getBudget(): Budget {
        val income = incomeRepository.getIncome().first()
        val budgetPlan = budgetPlanRepository.getSavedBudgetPlan().first()

        return Budget(income!!, budgetPlan!!)
    }

}