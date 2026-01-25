package com.andydotdaniel.jajanku.mocks

import com.andydotdaniel.jajanku.data.repository.BudgetPlanRepository
import com.andydotdaniel.jajanku.domain.BudgetPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BudgetPlanRepositoryMock: BudgetPlanRepository {

    var savedBudgetPlan: BudgetPlan? = null
    override fun getSavedBudgetPlan(): Flow<BudgetPlan?> {
        return flowOf(savedBudgetPlan)
    }

    var saveBudgetPlanCalledWithValue: BudgetPlan? = null
    override suspend fun saveBudgetPlan(plan: BudgetPlan) {
        saveBudgetPlanCalledWithValue = plan
    }

}