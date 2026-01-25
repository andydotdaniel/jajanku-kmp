package com.andydotdaniel.jajanku.mocks

import com.andydotdaniel.jajanku.data.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class IncomeRepositoryMock: IncomeRepository {

    var savedIncome: Double? = null
    override fun getIncome(): Flow<Double> {
        return flowOf(savedIncome!!)
    }

    var updateIncomeCalledWithValue: Double? = null
    override suspend fun updateIncome(value: Double) {
        updateIncomeCalledWithValue = value
    }

}