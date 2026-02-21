package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.data.AppDataStore
import com.andydotdaniel.jajanku.data.SETTINGS_BUDGET_PLAN
import com.andydotdaniel.jajanku.domain.BudgetPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface BudgetPlanRepository {
    fun getSavedBudgetPlan(): Flow<BudgetPlan?>
    suspend fun saveBudgetPlan(plan: BudgetPlan)
}

class AppBudgetPlanRepository(
    private val dataStore: AppDataStore
): BudgetPlanRepository {

    fun encodeBudgetPlan(budgetPlan: BudgetPlan): String {
        return "${budgetPlan.id}/${budgetPlan.needs}/${budgetPlan.wants}/${budgetPlan.savings}"
    }

    fun decodeBudgetPlan(string: String): BudgetPlan {
        val parts = string.split("/")
        return BudgetPlan(parts[0], parts[1].toFloat(), parts[2].toFloat(), parts[3].toFloat())
    }

    override fun getSavedBudgetPlan(): Flow<BudgetPlan?> {
        return dataStore.preferences.data.map { preferences ->
            val budgetPlan = preferences[SETTINGS_BUDGET_PLAN]
            if (budgetPlan.isNullOrEmpty()) null else decodeBudgetPlan(budgetPlan)
        }
    }

    override suspend fun saveBudgetPlan(plan: BudgetPlan) {
        dataStore.preferences.updateData {
            it.toMutablePreferences().also { preferences ->
                val stringValue = encodeBudgetPlan(plan)
                preferences[SETTINGS_BUDGET_PLAN] = stringValue
            }
        }
    }

}