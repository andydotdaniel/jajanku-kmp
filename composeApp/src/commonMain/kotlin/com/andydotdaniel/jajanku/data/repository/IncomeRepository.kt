package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.data.AppDataStore
import com.andydotdaniel.jajanku.data.SETTINGS_BUDGET_INCOME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IncomeRepository {
    fun getIncome(): Flow<Double?>
    suspend fun updateIncome(value: Double)
}

class AppIncomeRepository(
    private val dataStore: AppDataStore
): IncomeRepository {

    override fun getIncome(): Flow<Double?> {
        return dataStore.preferences.data.map { preferences ->
            preferences[SETTINGS_BUDGET_INCOME]
        }
    }

    override suspend fun updateIncome(value: Double) {
        dataStore.preferences.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[SETTINGS_BUDGET_INCOME] = value
            }
        }
    }

}