package com.andydotdaniel.jajanku.mocks

import com.andydotdaniel.jajanku.data.database.entities.ExpenseType
import com.andydotdaniel.jajanku.data.repository.ExpenseTypeRepository

class ExpenseTypeRepositoryMock: ExpenseTypeRepository {

    var expenseTypes = listOf<ExpenseType>()
    override suspend fun getExpenseTypes(): List<ExpenseType> {
        return expenseTypes
    }

}