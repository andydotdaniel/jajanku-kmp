package com.andydotdaniel.jajanku.data.repository

import com.andydotdaniel.jajanku.data.database.entities.ExpenseType
import com.andydotdaniel.jajanku.data.database.entities.ExpenseTypeDao

interface ExpenseTypeRepository {
    suspend fun getExpenseTypes(): List<ExpenseType>
}

class AppExpenseRepository(private val expenseTypeDao: ExpenseTypeDao): ExpenseTypeRepository {

    override suspend fun getExpenseTypes(): List<ExpenseType> {
        return expenseTypeDao.getAll()
    }

}